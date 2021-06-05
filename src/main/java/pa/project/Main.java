package pa.project;

import javafx.application.Application;
import javafx.stage.Stage;
import pa.project.manager.Manager;
import pa.project.repositories.WordsRepository;
import pa.project.windows.Window;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Window window = new Window();
        window.createStartWindow(primaryStage);
/*
        EntityManagerFactory entityManagerFactory = Manager.getFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        WordsRepository wordsRepository = new WordsRepository(entityManager);
        System.out.println("cel mai mare id este " + wordsRepository.getMaxId());*/
    }
}
