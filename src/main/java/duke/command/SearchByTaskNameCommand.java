package duke.command;
import duke.exception.DukeException;
import duke.task.*;
import duke.ui.Ui;

import java.util.LinkedList;

/**
 * It is a command object extends from Command for the Duke program.
 * When the parser calls it, it will receive the requests from the users
 * during the running of the program and starts to search the tasks that matches the name
 * user asks.
 */
public class SearchByTaskNameCommand extends Command{
    /**
     * Constructor for SearchByTaskNameCommand object
     *
     * @param userMessage The message that the user inputs for further execution.
     */
    public SearchByTaskNameCommand(String userMessage){
        super(userMessage);
    }

    /**
     * The execution after parsing, it will search relevant tasks based on the name.
     * If the input is not correct, it will raise an exception.
     *
     * @param taskList The current taskList in the program.
     * @param ui The current ui in the program.
     * @throws DukeException if there are some cases such as the input name format is wrong.
     */
    public void execute(TaskList taskList, Ui ui) throws DukeException {
        String[] info;
        String name;

        // prevent input mistakes.
        try{
            info = userMessage.split(" ",2);
            name = info[1];
        } catch (Exception e){
            throw new DukeException("The search input format is wrong, the format should be: \n" +
                    "search name <The task name>");
        }

        LinkedList<Task> tasks = taskList.getTasks();
        StringBuilder builder = new StringBuilder();
        builder.append("Here are the search results: \n");
        int numOfTasksFound = 0;

        // Search by loop
        for (Task single : tasks) {
            String singleName = single.getTaskName();
            if (singleName.contains(name)){
                builder.append("[" + single.getStatusIcon() + "]" + single.toString() + "\n");
                numOfTasksFound++;
            }
        }
        if (numOfTasksFound == 0){
            throw new DukeException("OOPS! There is no task that matches the name.");
        }

        String botMessage = builder.toString();
        ui.display(botMessage);

    }
}
