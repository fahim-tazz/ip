package duke.backend;

import duke.exceptions.NoArgumentException;
import duke.tasks.*;

import java.time.format.DateTimeParseException;
import java.util.StringTokenizer;

public class Parser {

    //TODO: make static import from duke.backend.UI.
    public static final String DIVIDER = "____________________________________________________________\n";
    private final UI ui;
    private final TaskList taskManager;

    public Parser(UI ui, TaskList taskManager) {
        this.ui = ui;
        this.taskManager = taskManager;
    }

    public void parse(String instr){
//        Single-word instructions
        switch (instr) {
        case "list":  //List down
        ui.list();
        break;

        case "bye":   //Exit
            ui.bye();
            break;

        default:                                      //Instructions with arguments
            StringTokenizer tokens = new StringTokenizer(instr, " ");
            final String action = tokens.nextToken(); //Splitting the action and args.

            StringBuilder keyword = new StringBuilder();  //keyword = argument(s) after the action.
            while (tokens.hasMoreTokens()) {
                keyword.append(" ")
                        .append(tokens.nextToken());
            }
            keyword = new StringBuilder(keyword.toString()
                                                .strip());

        try {
            switch (action) {                            //For instructions with argument(s).System.out.println(DIVIDER + "OOPS! I'm sorry, but I don't know what that means :-(\n" + DIVIDER);  //For unknown action.
            case "mark":
                try {
                    Task changedTask = taskManager.mark(Integer.parseInt(keyword.toString()) - 1);
                    System.out.println(DIVIDER + "Nice! I've marked this task as done:\n" + changedTask + "\n" + DIVIDER);
//                    return null;
                } catch (IndexOutOfBoundsException ioobe) {
                    System.out.println("Sorry, the index number you've entered does not exist.");
                } catch (NumberFormatException nfe) {
                    System.out.println("Please enter the index number you wish to mark/unmark.");
                }
                break;

            case "unmark":
                try {
                    Task changedTask = taskManager.unmark(Integer.parseInt(keyword.toString()) - 1);
                    System.out.println(DIVIDER + "OK! I've marked this task as not done yet:\n" + changedTask + "\n" + DIVIDER);
//                    return null;
                } catch (IndexOutOfBoundsException ioobe) {
                    System.out.println("Sorry, the index number you've entered does not exist.");
                } catch (NumberFormatException nfe) {
                    System.out.println("Please enter the index number you wish to mark/unmark.");
                }
                break;

            case "todo":
                if (keyword.toString().equals("")) {
                    throw new NoArgumentException();
                }
                taskManager.add(new Todo(keyword.toString()));
                break;

            case "deadline":
                String[] deadlineFinder = keyword.toString()
                        .split(" /by "); //Split keyword into description and date, separated by "/by".
                keyword = new StringBuilder(deadlineFinder[0]);
                try {
                    String deadline = deadlineFinder[1];
                    taskManager.add(new Deadline(keyword.toString(), deadline));
                } catch (IndexOutOfBoundsException ioobe) {
                    System.out.println("Please define a name and a deadline following the keyword '/by'.");
                } catch (DateTimeParseException dtpe) {
                    System.out.println("Please enter a valid date in the format YYYY-MM-DD.");
                }
                break;

            case "event":
                String[] startFinder = keyword.toString()
                        .split(" /from ");  //Split keyword into description and start,end.
                try {
                    String[] endFinder = startFinder[1].split(" /to "); //Split start,end into start and end.
                    taskManager.add(new Event(startFinder[0], endFinder[0], endFinder[1]));
                } catch (IndexOutOfBoundsException ioobe) {
                    System.out.println("Please define a name, a start time following the keyword '/from'\nand then an end time following the keyword '/to'.");
                }
                break;

            case "delete":
                try {
                    int deleteIdx = Integer.parseInt(keyword.toString());
                    Task deletedTask = taskManager.delete(deleteIdx - 1);
                    System.out.println(DIVIDER);
                    System.out.println("OK! I've deleted the following task: " + deletedTask + "\n" + DIVIDER);
                } catch (NumberFormatException nfe) {
                    System.out.println("Please enter the index number you wish to delete.");
                } catch (IndexOutOfBoundsException ioobe) {
                    System.out.println("Sorry, the index number you've entered does not exist.");
                }
                break;
            default:
                System.out.println(DIVIDER + "OOPS! I'm sorry, but I don't know what that means :-(\n" + DIVIDER);  //For unknown action.
            }
        } catch (NoArgumentException nae) {
            System.out.println("Please enter a name for your task.");
        }
    }
    }
}