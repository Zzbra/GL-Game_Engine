import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Stage;

public class Core extends AnimationTimer {
    private long deltaTime;
    @Override
    public void handle(long deltaTime) {
        this.setDeltaTime(deltaTime);
        //isRunning = GameLogic->doLogic();
        //renderer.draw();
    }

    private void setDeltaTime(long deltaTime){
        this.deltaTime = deltaTime;
    }
}