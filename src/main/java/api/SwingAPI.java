package api;


public class SwingAPI {

    public static SwingListener getListenerMethods() {
        return SwingListener.getInstance();
    }


    protected static SwingRenderer getRendererMethods() {
        return SwingRenderer.getInstance();
    }

    protected static SwingWindow getWindowMethods() {
        return SwingWindow.getInstance().getInstance();
    }
}
