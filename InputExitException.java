//this is custom exception which handles the input of user to EXIT
class InputExitException extends RuntimeException {
    public InputExitException() {
        super("Exiting...");
    }
}