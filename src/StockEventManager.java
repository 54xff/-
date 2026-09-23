import java.util.ArrayList;
import java.util.List;

public class StockEventManager {
    private static List<StockChangeListener> listeners = new ArrayList<>();

    public static void addListener(StockChangeListener l) {
        listeners.add(l);
    }

    public static void notifyStockChange() {
        for (StockChangeListener l : listeners) {
            l.onStockChange();
        }
    }
}