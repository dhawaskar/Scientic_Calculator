# Scientific Calculator

#### Team Members:
## Sandesh D Sathyanarayana, Gautham Kashim, Hasil Sharma

## Basic Description:
 	
A Scientific Calculator is an Android application that implements the mathematical functionalities like trigonometric operations, Number systems (bases like Binary, hexadecimal, and decimal), Matrix operations, Complex Number operations, and Combinatorics. It also supports basic arithmetic operations like addition, subtraction, multiplication, division, logarithms, and square roots, etc. 
Few important functionalities in each of the screen are listed below.

* Trigonometric: sin, cos, tan, sinh, cosh, and tanh both in degrees and radians
* Number System: Conversion between Binary, decimal, and Hexadecimal
* Combinatorics: permutations, combinations, generic powers, logarithms, Nth root etc.
* Matrix Operations: Addition, subtraction, transpose, inverse, and determinant
* Complex Numbers: Addition, subtraction, multiplication, inverse and division etc.
* Compound operations are also supported for all the above-mentioned functions.

## Description of files:

* `app/src`: main source code of our application.
* `main/ooad`: Folder which has the main source code built using the principles of Object-oriented design. It has two parts front end and back end.
#### Back End
* `backend/data/types`: This folder has the files needed for all the data types of the backend source code Ex: double, integer and type information of each object. We have decoupled the all possible data elements we would use in our operations.
* `ComplexElem.java`: Complex data elements of our code.
* `DoubleElem.java`: Double data type elements decoupling
* `ElemFactory.java`, `ElemType.java`, `ElemTypeEnum.java`: Elementary type of elements like integers, enum, etc. 

* `IntegerElem.java`: Integer elements
* `OperatorEnum.java`: operators’ elements of the code.
* `backend/data/operators`: This folder has the operator’s information, used in our code. This is where we have decoupled the usage of operators and the number of arguments that they take.
* `backend/la/matrix.java`: File that has all the functionalities we would use in the matrix operations.
* `backend/la/exceptions`: This folder has the files regarding the exceptions we would use in our backend code. We have decoupled the exceptions so they could be independently used in the code. Following are the files for the same reason - `IncompatibleMatricesException.java`, `MatrixNonSquareException.java`,`NonInvertibleException.java`, `SingularMatrixException.java`
#### Front End
* frontend/screens/: There are five screens in our application and they share common steps and are captured in “CommonScreenElements.java” 
* `CombinatoricsScreen.java`: Java code for combinatorics operations
* `ComplexOperationsScreen.java`: Complex number systems Java activity file
* `MatrixOperationsScreen.java`: Matrix operation activity java code
* `NumberSystemScreen.java`: Number System operations activity java code
* `TrignoScreen.java`: Trigonometric operations activity java code
* `app/src/main/AndroidManifest.xml`: Manifest file of Android application to link the activities and their layouts.
* `App/main/res`: All the resources needed to build the front-end layout of the applications

### Layout: Directory that has all the front end code of our screen layouts
* `/app/res/layout/activity_combinatorics.xml`: Layout code for Combinatorics operations screen
* `~/activity_complex.xml`: Layout code for Complex numbers operations screen
* `~/activity_matrixoperations.xml`: Layout code for Matrices operations screen
* `~/activity_number_system.xml`: Layout code for Number System with different bases operations screen
* `~/activity_trigonometric.xml`: Layout code for trigonometric operations screen
* `app/main/res/values`: Folder which has files with all the string constant being defined and used in the layout design.
* `~/Colors.xml`: XML file which has color constants
* `~/Dimen.xml`: XML file which has dimensions constants
* `~/Strings.xml`: XML file which has strings constants
* `~/Styles.xml`: XML file which has a style of CSS file constants
* `~/app/main/mipmap *`: Folder that has the images needed to create the basic layout of the main Android application.
* `~/app/main/res/drawable`: Files and images being used in the layout creations.
* `~/Myborder_grey`: grey colored button structurer file used in all layouts
* `~/Myborder_yellow.xml`: grey colored button structurer file used in all layouts
* `~/myborder_black.xml`: Common xml file that defines the border structure for black colored buttons created in all layouts
* `test/java/com/ooad/frontend`: Folder that has “ExampleUnitTest.java” that has unit test to test out front end creation of layouts.
* `androidTest/java/com/ooad/frontend`: Folder that has “ExampleInstrumentedTest.java” that has unit sample program test for front end creation of layouts.

#### Report: Folder that has the report of our project

* Gradle files: Files used to build the android application
* `Gradle/build` : Has the information related to build, android sdk version and application dependencies.

## Notes on installing or executing: 
	To run our Android application, one would need an android smartphone or could use emulator in Android Studio.
* Step 1: [Download and install Android Studio ID](https://developer.android.com/studio/install)
* Step 2: [Download our Android  code from the Git repository](https://github.com/dhawaskar/Scientic_Calculator)
* Step 3: In the Android studio click “Open existing project” and open our downloaded application from step 2.
* Step 4: Build the application. Application can be run using USB connected Android device or using emualtor image downloaded using Android Studio(steps can be seen when one runs the application : follow the prompt)

## Running Instructions
* User Interface is similar to typical calculator applications in our smartphones.
* Text Box hints the user about the operation. User entered input is seen in the textbox.
* `C` can be used to clear the input screen.
* `DEL` can be used to clear the operation.
* `Unary' operations are typically solved by entering the number and the unary operator button.
* `Binary operator` can be typically solved using the `equal to : =` Button.
* Complex expressions can be entered by user and brackets should be taken care by user.
* Invalid expressions shows appropriate error statements.
* Matrix Operations: User has to enter the size of the matrix first and then the display prompts user to enter the number of elements of that particular size matrix
* For Single Matrix operation - User can press the appropriate button after the matrix input - Our application gives output for last entered matrix .
* For Double Matrix operation: User has to enter the size and elements of matrix of the twice and the intended operator button. App evaluates the operation for the last two matrices and gives the output.

