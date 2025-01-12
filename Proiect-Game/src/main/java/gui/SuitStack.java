package gui;


import cards.Card;
import cards.CardImages;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.StackPane;
import model.CardDragHandler;
import model.FinaleStackOrder;
import model.GameModel;
import model.GameModelListener;

public class SuitStack extends StackPane implements GameModelListener {
    private static final int PADDING = 5;
    private static final String BORDER_STYLE = "-fx-border-color: lightgray;-fx-border-width: 3;-fx-border-radius: 10.0";
    private static final String BORDER_STYLE_DRAGGED = "-fx-border-color: darkgray;-fx-border-width: 3; -fx-border-radius: 10.0";
    private static final String BORDER_STYLE_NORMAL = "-fx-border-color: lightgray;-fx-border-width: 3; -fx-border-radius: 10.0";


    private CardDragHandler aDragHandler;
    private FinaleStackOrder aIndex;

    public SuitStack(FinaleStackOrder pIndex) {
        aIndex = pIndex;
        setPadding(new Insets(PADDING));
        setStyle(BORDER_STYLE);
        final ImageView image = new ImageView(CardImages.getBack());
        image.setVisible(false);
        getChildren().add(image);
        aDragHandler = new CardDragHandler(image);
        image.setOnDragDetected(aDragHandler);
        setOnDragOver(createOnDragOverHandler(image));
        setOnDragEntered(createOnDragEnteredHandler());
        setOnDragExited(createOnDragExitedHandler());
        setOnDragDropped(createOnDragDroppedHandler());
        GameModel.instance().addListener(this);
    }

    @Override
    public void gameStateChanged() {
        if (GameModel.instance().isFinalePileStackEmpty(aIndex)) {
            getChildren().get(0).setVisible(false);
        } else {
            getChildren().get(0).setVisible(true);
            Card topCard = GameModel.instance().peekSuitStack(aIndex);
            ImageView image = (ImageView) getChildren().get(0);
            image.setImage(CardImages.getCard(topCard));
            aDragHandler.setCard(topCard);
        }
    }

    private EventHandler<DragEvent> createOnDragOverHandler(final ImageView pView) {
        return new EventHandler<DragEvent>() {
            public void handle(DragEvent pEvent) {
                if (pEvent.getGestureSource() != pView && pEvent.getDragboard().hasString()) {
                    CardTransfer transfer = new CardTransfer(pEvent.getDragboard().getString());
                    if (transfer.size() == 1 && GameModel.instance().isLegalMove(transfer.getTop(), aIndex)) {
                        pEvent.acceptTransferModes(TransferMode.MOVE);
                    }
                }

                pEvent.consume();
            }
        };
    }

    private EventHandler<DragEvent> createOnDragEnteredHandler() {
        return new EventHandler<DragEvent>() {
            public void handle(DragEvent pEvent) {
                CardTransfer transfer = new CardTransfer(pEvent.getDragboard().getString());
                if (transfer.size() == 1 && GameModel.instance().isLegalMove(transfer.getTop(), aIndex)) {
                    setStyle(BORDER_STYLE_DRAGGED);
                }
                pEvent.consume();
            }
        };
    }

    private EventHandler<DragEvent> createOnDragExitedHandler() {
        return new EventHandler<DragEvent>() {
            public void handle(DragEvent pEvent) {
                setStyle(BORDER_STYLE_NORMAL);
                pEvent.consume();
            }
        };
    }

    private EventHandler<DragEvent> createOnDragDroppedHandler() {
        return new EventHandler<DragEvent>() {
            public void handle(DragEvent pEvent) {
                Dragboard db = pEvent.getDragboard();
                boolean success = false;
                if (db.hasString()) {
                    CardTransfer transfer = new CardTransfer(pEvent.getDragboard().getString());
                    GameModel.instance().getCardMove(transfer.getTop(), aIndex).executeMove();
                    success = true;
                }
                pEvent.setDropCompleted(success);
                pEvent.consume();
            }
        };
    }
}