package utility;

public abstract class AbstractRoom implements RoomInterface {
    private boolean window;

    public AbstractRoom(boolean lengthy) {
        this.window = window;
    }

    @Override
    public boolean window() {
        return window;
    }

    @Override
    public void setwindow(boolean window) {
        this.window = window;
    }
}
