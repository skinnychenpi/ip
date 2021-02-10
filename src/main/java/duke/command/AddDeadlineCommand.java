package duke.command;
import duke.exception.DukeException;
import duke.task.Deadline;
import duke.task.TaskList;
/**
 * It is a command object extends from Command for the Duke program.
 * When the parser calls it, it will receive the requests from the users
 * during the running of the program and starts add new deadlines to the task list.
 */
public class AddDeadlineCommand extends Command {

    /**
     * Constructor for AddDeadlineCommand object
     *
     * @param userMessage The message that the user inputs for further execution.
     */
    public AddDeadlineCommand(String userMessage) {
        super(userMessage);
    }

    /**
     * The execution after parsing, it will add a deadline object into the tasks.
     * If the input is not correct, it will raise an exception.
     *
     * @param taskList The current taskList in the program.
     * @return The Duke robot massage to the GUI.
     * @throws DukeException if there are some cases such as no deadline time specified, no deadline task name, then
     * it will raise the DukeException.
     */
    public String execute(TaskList taskList) throws DukeException {
        StringBuilder builder = new StringBuilder();
        builder.append("Got it! I've added this task:\n");

        int spaceIndex = userMessage.indexOf(" ");
        int dateIndex = userMessage.indexOf('/');
        if (dateIndex == -1) {
            throw new DukeException("OOPS!!! I can't find your deadline time.");
        }
        if (spaceIndex == -1 || dateIndex - spaceIndex == 1) {
            throw new DukeException("OOPS!!! The description of a deadline cannot be empty.");
        }

        String deadlineName = userMessage.substring(spaceIndex + 1, dateIndex - 1);
        String by = userMessage.substring(dateIndex + 4);
        Deadline deadline;
        try {
            deadline = new Deadline(deadlineName, by);
        } catch (Exception e) {
            throw new DukeException("OOPS! The input format is wrong! Should be YYYY-MM-DD HH:MM");
        }
        taskList.addTask(deadline);

        builder.append("[" + deadline.getStatusIcon() + "] " + deadline.toString());
        builder.append("\nNow you have " + taskList.getNumOfTasks() + " tasks in the list.");
        String botMessage = builder.toString();
        return botMessage;
    }
}
