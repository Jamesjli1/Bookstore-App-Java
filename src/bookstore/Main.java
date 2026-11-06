package bookstore;

/**
 * Entry point of BookStore application.
 * Initializes data and launches the GUI.
 */
public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            StoreData data = DataManager.loadData();
            BookStoreFrame frame = new BookStoreFrame(data);
            frame.setVisible(true);
        });
    }
}