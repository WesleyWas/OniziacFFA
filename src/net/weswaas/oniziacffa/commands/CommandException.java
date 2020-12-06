package net.weswaas.oniziacffa.commands;

public class CommandException extends Exception{

    private static final long serialVersionUID = -3144699204650921522L;

    /**
     * Create a new CommandException
     *
     * @param message The error message.
     */
    public CommandException(String message) {
        super(message);
    }

}

