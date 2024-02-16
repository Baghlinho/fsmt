//
// The contents of this file are subject to the Mozilla Public
// License Version 1.1 (the "License"); you may not use this file
// except in compliance with the License. You may obtain a copy
// of the License at http://www.mozilla.org/MPL/
//
// Software distributed under the License is distributed on an
// "AS IS" basis, WITHOUT WARRANTY OF ANY KIND, either express or
// implied. See the License for the specific language governing
// rights and limitations under the License.
//
// The Original Code is State Machine Compiler (SMC).
//
// The Initial Developer of the Original Code is Charles W. Rapp.
// Portions created by Charles W. Rapp are
// Copyright (C) 2006 - 2008. Charles W. Rapp.
// All Rights Reserved.
//
// Contributor(s):
//   Eitan Suez contributed examples/Ant.
//   (Name withheld) contributed the C# code generation and
//   examples/C#.
//   Francois Perrad contributed the Python code generation and
//   examples/Python.
//   Chris Liscio contributed the Objective-C code generation
//   and examples/ObjC.
//
// RCS ID
// $Id: SmcHeaderObjCGenerator.java,v 1.11 2015/08/02 19:44:36 cwrapp Exp $
//
// CHANGE LOG
// (See the bottom of this file.)
//

package net.sf.smc.generator;

import java.util.Iterator;
import java.util.List;
import net.sf.smc.model.SmcAction;
import net.sf.smc.model.SmcElement;
import net.sf.smc.model.SmcFSM;
import net.sf.smc.model.SmcMap;
import net.sf.smc.model.SmcParameter;
import net.sf.smc.model.SmcState;
import net.sf.smc.model.SmcTransition;
import net.sf.smc.model.SmcVisitor;

/**
 * Visits the abstract syntax tree emitting an Objective C header
 * file.
 * @see SmcElement
 * @see SmcVisitor
 * @see SmcCppGenerator
 * @see SmcOptions
 *
 * @author <a href="mailto:rapp@acm.org">Charles Rapp</a>
 */

public final class SmcHeaderObjCGenerator
    extends SmcCodeGenerator
{
//---------------------------------------------------------------
// Member data
//

//---------------------------------------------------------------
// Member methods
//

    //-----------------------------------------------------------
    // Constructors.
    //

    /**
     * Creates an Objective C header code generator for the given
     * options.
     * @param options The target code generator options.
     */
    public SmcHeaderObjCGenerator(final SmcOptions options)
    {
        super (options, DEFAULT_HEADER_SUFFIX);
    } // end of SmcHeaderObjCGenerator(SmcOptions)

    //
    // end of Constructors.
    //-----------------------------------------------------------

    //-----------------------------------------------------------
    // SmcVisitor Abstract Method Impelementation.
    //

    /**
     * Emits Objective C header code for the finite state
     * machine.
     * @param fsm emit Objective C header code for this finite
     * state machine.
     */
    @Override
    public void visit(SmcFSM fsm)
    {
        String context = fsm.getContext();
        String fsmClassName = fsm.getFsmClassName();
        String mapName;
        List<SmcTransition> transList;
        String separator;
        Iterator<SmcParameter> pit;

        mTarget.println("/*");
        mTarget.println(" * ex: set ro:");
        mTarget.println(" * DO NOT EDIT.");
        mTarget.println(" * generated by smc (http://smc.sourceforge.net/)");
        mTarget.print(" * from file : ");
        mTarget.print(mSrcfileBase);
        mTarget.println(".sm");
        mTarget.println(" */");
        mTarget.println();

        // Include required standard .h files.
        mTarget.println();
        mTarget.println("#import \"statemap.h\"");

        mTarget.println();

        // Forward declare all the state classes in all the maps.
        mTarget.print(mIndent);
        mTarget.println("// Forward declarations.");
        for (SmcMap map: fsm.getMaps())
        {
            mapName = map.getName();

            // class <map name>;
            mTarget.print(mIndent);
            mTarget.print("@class ");
            mTarget.print(mapName);
            mTarget.println(";");

            // Iterate over the map's states.
            for (SmcState state: map.getStates())
            {
                mTarget.print(mIndent);
                mTarget.print("@class ");
                mTarget.print(mapName);
                mTarget.print("_");
                mTarget.print(state.getClassName());
                mTarget.println(";");
            }

            // Forward declare the default state as well.
            mTarget.print(mIndent);
            mTarget.print("@class ");
            mTarget.print(mapName);
            mTarget.println("_Default;");
        }

        // Forward declare the state class and its
        // context as well.
        mTarget.print(mIndent);
        mTarget.print("@class ");
        mTarget.print(context);
        mTarget.println("State;");
        mTarget.print(mIndent);
        mTarget.print("@class ");
        mTarget.print(fsmClassName);
        mTarget.println(";");

        // Forward declare the application class.
        printContextForwardDeclaration(context);

        // Do user-specified forward declarations now.
        for (String declaration: fsm.getDeclarations())
        {
            mTarget.print(mIndent);
            mTarget.print(declaration);

            // Add a semicolon if the user did not use one.
            if (declaration.endsWith(";") == false)
            {
                mTarget.print(";");
            }

            mTarget.println();
        }
        mTarget.println();

        // Declare user's base state class.
        mTarget.print(mIndent);
        mTarget.print("@interface ");
        mTarget.print(context);
        mTarget.println("State : SMCState");
        mTarget.println("{");
        mTarget.println("}");

        // Add the default Entry() and Exit() definitions.
        mTarget.print(mIndent);
        mTarget.print("- (void)Entry:(");
        mTarget.print(fsmClassName);
        mTarget.println("*)context;");
        mTarget.print(mIndent);
        mTarget.print("- (void)Exit:(");
        mTarget.print(fsmClassName);
        mTarget.println("*)context;");

        mTarget.println();

        // Print out the default definitions for all the
        // transitions. First, get the transitions list.
        transList = fsm.getTransitions();

        // Output the global transition declarations.
        for (SmcTransition trans: transList)
        {
            // Don't output the default state here.
            if (trans.getName().equals("Default") == false)
            {
                mTarget.print(mIndent);
                mTarget.print("- (void)");
                mTarget.print(trans.getName());
                mTarget.print(":(");
                mTarget.print(fsmClassName);
                mTarget.print("*)context");

                for (SmcParameter param:
                         trans.getParameters())
                {
                    mTarget.print(" :");
                    param.accept(this);
                }

                mTarget.println(";");
            }
        }

        // Declare the global Default transition.
        mTarget.println("");
        mTarget.print(mIndent);
        mTarget.print("- (void)Default:(");
        mTarget.print(fsmClassName);
        mTarget.println("*)context;");

        // The base class has been defined.
        mTarget.print(mIndent);
        mTarget.println("@end");
        mTarget.println();

        // Generate the map classes. The maps will, in turn,
        // generate the state classes.
        for (SmcMap map: fsm.getMaps())
        {
            map.accept(this);
        }

        // Generate the FSM context class.
        mTarget.print(mIndent);
        mTarget.print("@interface ");
        mTarget.print(fsmClassName);
        mTarget.println(" : SMCFSMContext");
        mTarget.print(mIndent);
        mTarget.println("{");

        mTarget.print(mIndent);
        mTarget.print("    __weak ");
        printContextType(context);
        mTarget.println(" _owner;");

        mTarget.print(mIndent);
        mTarget.println("}");

        mTarget.print(mIndent);
        mTarget.print("- (id)initWithOwner:(");
        printContextType(context);
        mTarget.print(")");
        mTarget.println("owner;");

        mTarget.print(mIndent);
        mTarget.print("- (id)initWithOwner:(");
        printContextType(context);
        mTarget.print(")");
        mTarget.println("owner state:(SMCState*)aState;");

        mTarget.print(mIndent);
        mTarget.print("- (");
        printContextType(context);
        mTarget.println(")owner;");

        mTarget.print(mIndent);
        mTarget.print("- (" );
        mTarget.print(context);
        mTarget.println("State*)state;");

        mTarget.println();

        mTarget.print(mIndent);
        mTarget.println("- (void)enterStartState;");
        mTarget.println();

        // Generate a method for every transition in every map
        // *except* the default transition.
        for (SmcTransition trans: transList)
        {
            if (trans.getName().equals("Default") == false)
            {
                SmcParameter param;

                mTarget.print(mIndent);
                mTarget.print("- (void)");
                mTarget.print(trans.getName());

                for (pit = (trans.getParameters()).iterator(),
                       separator = ":";
                     pit.hasNext();
                     separator = " :")
                {
                    param = pit.next();

                    mTarget.print(separator);
                    param.accept(this);
                }
                mTarget.println(";");
            }
        }

        // End the context class.
        mTarget.print(mIndent);
        mTarget.println("@end");
        mTarget.println();

        mTarget.println();
        mTarget.println("/*");
        mTarget.println(" * Local variables:");
        mTarget.println(" *  buffer-read-only: t");
        mTarget.println(" * End:");
        mTarget.println(" */");

        return;
    } // end of visit(SmcFSM)

    /**
     * Generates the map class declaration and then the state
     * classes:
     * <pre>
     *   <code>
     * class <i>map name</i>
     * {
     * public:
     *
     *     static <i>map name</i>_<i>state name</i> <i>state name</i>;
     * };
     *   </code>
     * </pre>
     * @param map emit Objective C header code for this map.
    */
    @Override
    public void visit(SmcMap map)
    {
        String context = map.getFSM().getContext();
        String mapName = map.getName();
        String stateName;

        mTarget.print(mIndent);
        mTarget.print("@interface ");
        mTarget.print(mapName);
        mTarget.println(" : NSObject");
        mTarget.print(mIndent);
        mTarget.println("{");
        mTarget.println("}");

        // Define class methods to access the state instances
        for (SmcState state: map.getStates())
        {
            stateName = state.getClassName();

            mTarget.print(mIndent);
            mTarget.print("+ (");
            mTarget.print(mapName);
            mTarget.print("_");
            mTarget.print(stateName);
            mTarget.print("*)");
            mTarget.print(stateName);
            mTarget.println(";");
        }

        // The map class is now defined.
        mTarget.print(mIndent);
        mTarget.println("@end");
        mTarget.println();

        // Declare the map's default state class.
        //
        // @interface <map name>_Default : <context>State
        // {
        // }
        // (user-defined Default state transitions.)
        // @end

        mTarget.print(mIndent);
        mTarget.print("@interface ");
        mTarget.print(mapName);
        mTarget.print("_Default : ");
        mTarget.print(context);
        mTarget.println("State");
        mTarget.print(mIndent);
        mTarget.println("{");
        mTarget.print(mIndent);
        mTarget.println("}");

        // Declare the user-defined default transitions first.
        if (map.hasDefaultState())
        {
            SmcState defaultState = map.getDefaultState();

            for (SmcTransition transition:
                     defaultState.getTransitions())
            {
                transition.accept(this);
            }
        }

        // The map's default state class is now defined.
        mTarget.print(mIndent);
        mTarget.println("@end");
        mTarget.println();

        // Now output the state class declarations.
        for (SmcState state: map.getStates())
        {
            state.accept(this);
        }

        return;
    } // end of visit(SmcMap)

    /**
     * Generates the state class declaration.
     * <pre>
     *   <code>
     * {@literal @interface} <i>map name</i>_<i>state name</i> : <i>map name</i>_Default
     * {
     * }
     * - (id)initWithName(NSString*)name stateId:(int)stateId;
     * (declare the transition methods.)
     * - (void)<i>transition name</i>:(<i>context</i>*)context <i>args</i>;
     * {@literal @end}
     *   </code>
     * </pre>
     * @param state emits Objective C header code for this state.
     */
    @Override
    public void visit(SmcState state)
    {
        SmcMap map = state.getMap();
        String fsmClassName = map.getFSM().getFsmClassName();
        String mapName = map.getName();
        String stateName = state.getClassName();
        List<SmcAction> actions;

        mTarget.print(mIndent);
        mTarget.print("@interface ");
        mTarget.print(mapName);
        mTarget.print('_');
        mTarget.print(stateName);
        mTarget.print(" : ");
        mTarget.print(mapName);
        mTarget.println("_Default");
        mTarget.print(mIndent);
        mTarget.println("{");
        mTarget.println("}");

        // Add the Entry() and Exit() methods if this state
        // defines them.
        actions = state.getEntryActions();
        if (actions != null && actions.size() > 0)
        {
            mTarget.print(mIndent);
            mTarget.print(" -(void)Entry:(");
            mTarget.print(fsmClassName);
            mTarget.println("*)context;");
        }

        actions = state.getExitActions();
        if (actions != null && actions.size() > 0)
        {
            mTarget.print(mIndent);
            mTarget.print(" -(void)Exit:(");
            mTarget.print(fsmClassName);
            mTarget.println("*)context;");        }

        // Now generate the transition methods.
        for (SmcTransition transition: state.getTransitions())
        {
            transition.accept(this);
        }

        // End of the state class declaration.
        mTarget.print(mIndent);
        mTarget.println("@end");
        mTarget.println();

        return;
    } // end of visit(SmcState)

    /**
     * Generates the transition method declaration:
     * <pre>
     *   <code>
     * - (void)<i>transition name</i>:(<i>context</i>Context*)context <i>args</i>;
     *   </code>
     * </pre>
     * @param transition emits Groovy code for this state transition.
     */
    @Override
    public void visit(SmcTransition transition)
    {
        SmcState state = transition.getState();

        mTarget.print(mIndent);
        mTarget.print("- (void)");
        mTarget.print(transition.getName());
        mTarget.print(":(");
        mTarget.print(
            state.getMap().getFSM().getFsmClassName());
        mTarget.print("*)context");

        // Add user-defined parameters.
        for (SmcParameter parameter: transition.getParameters())
        {
            mTarget.print(" :");
            parameter.accept(this);
        }

        // End of transition method declaration.
        mTarget.println(";");

        return;
    } // end of visit(SmcTransition)

    /**
     * Emits Objective C header code for this transition
     * parameter.
     * @param parameter emits Objective C header code for this
     * transition parameter.
     */
    @Override
    public void visit(SmcParameter parameter)
    {
        mTarget.print("(");
        mTarget.print(parameter.getType());
        mTarget.print(")");
        mTarget.print(parameter.getName());

        return;
    } // end of visit(SmcParameter)

    //
    // end of SmcVisitor Abstract Method Impelementation.
    //-----------------------------------------------------------

    /**
     * Outputs either "@protocol" or "@class" based on whether
     * the "-protocol" command line option was specified.
     * @param context context class name.
     */
    private void printContextForwardDeclaration(final String context)
    {
        mTarget.print(mIndent);
        if (mUseProtocolFlag)
        {
            mTarget.print("@protocol ");
        }
        else
        {
            mTarget.print("@class ");
        }

        mTarget.print(context);
        mTarget.println(";");

        return;
    } // end of printContextForwardDeclaration(String)

    /**
     * Outputs the appropriate context declaration based on
     * whether the "-protocol" command line option was specified
     * or not.
     * @param context context class name.
     */
    private void printContextType(final String context)
    {
        if (mUseProtocolFlag)
        {
            mTarget.print("id<");
            mTarget.print(context);
            mTarget.print(">");
        }
        else
        {
            mTarget.print(context);
            mTarget.print("*");
        }

        return;
    } // end of printContextType(String)
} // end of class SmcHeaderObjCGenerator

//
// CHANGE LOG
// $Log: SmcHeaderObjCGenerator.java,v $
// Revision 1.11  2015/08/02 19:44:36  cwrapp
// Release 6.6.0 commit.
//
// Revision 1.10  2015/02/16 21:43:09  cwrapp
// SMC v. 6.5.0
//
// SMC - The State Machine Compiler v. 6.5.0
//
// Major changes:
//
// (Java)
//     Added a new "-java7" target language. This version represents
//     the FSM as a transition table. The transition table maps the
//     current state and the transition to a
//     java.lang.invoke.MethodHandle. The transition is executed by
//     calling MethodHandle.invokeExact, which is only slightly
//     slower than a compiled method call.
//
//     The -java7 generated code is compatible with -java generated
//     code. This allows developers to switch between the two
//     without changing application code.
//
//     NOTE: -java7 requires Java 1.7 or latter to run.
//
//
// Minor changes:
//
// (None.)
//
//
// Bug Fixes:
//
// (Objective-C)
//     Incorrect initWithOwner body generated. Same fundamental
//     problem as SF bug 200. See below.
//     (SF bug 198)
//
// (Website)
//     Corrected broken link in FAQ page.
//     (SF bug 199)
//
// (C++)
//     Corrected the invalid generated FSM class name.
//     (SF bug 200)
//
// (C)
//     EXIT_STATE() #define macro not generated.
//     (SF bug 201)
//
// (Manual)
//     Corrected examples which showed %fsmclass and %map set to the
//     same name. This is invalid for most target languages since
//     that would mean the nested map class would have the same name
//     as the containing FSM class.
//
//
//
// ++++++++++++++++++++++++++++++++++++++++
//
// If you have any questions or bugs, please surf
// over to http://smc.sourceforge.net and check out
// the discussion and bug forums. Note: you must be
// a SourceForge member to add articles or bugs. You
// do not have to be a member to read posted
// articles or bugs.
//
// Revision 1.9  2013/09/02 14:45:57  cwrapp
// SMC 6.3.0 commit.
//
// Revision 1.8  2013/07/14 14:32:38  cwrapp
// check in for release 6.2.0
//
// Revision 1.7  2009/11/25 22:30:19  cwrapp
// Fixed problem between %fsmclass and sm file names.
//
// Revision 1.6  2009/11/24 20:42:39  cwrapp
// v. 6.0.1 update
//
// Revision 1.5  2009/09/12 21:44:49  kgreg99
// Implemented feature req. #2718941 - user defined generated class name.
// A new statement was added to the syntax: %fsmclass class_name
// It is optional. If not used, generated class is called as before "XxxContext" where Xxx is context class name as entered via %class statement.
// If used, generated class is called asrequested.
// Following language generators are touched:
// c, c++, java, c#, objc, lua, groovy, scala, tcl, VB
// This feature is not tested yet !
// Maybe it will be necessary to modify also the output file name.
//
// Revision 1.4  2009/09/05 15:39:20  cwrapp
// Checking in fixes for 1944542, 1983929, 2731415, 2803547 and feature 2797126.
//
// Revision 1.3  2009/04/10 14:02:48  cwrapp
// Set initial state via initializer.
//
// Revision 1.2  2009/03/27 09:41:47  cwrapp
// Added F. Perrad changes back in.
//
// Revision 1.1  2009/03/01 18:20:42  cwrapp
// Preliminary v. 6.0.0 commit.
//
// Revision 1.3  2008/03/21 14:03:16  fperrad
// refactor : move from the main file Smc.java to each language generator the following data :
//  - the default file name suffix,
//  - the file name format for the generated SMC files
//
// Revision 1.2  2007/02/21 13:55:27  cwrapp
// Moved Java code to release 1.5.0
//
// Revision 1.1  2007/01/15 00:23:51  cwrapp
// Release 4.4.0 initial commit.
//