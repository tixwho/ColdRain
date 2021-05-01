package learnGUI;


/**
* Created by Administrator on 2018/6/22 0022.
* 下面这些javafx.*下面的API都是JDK8、JRE8中内置好的，直接调用即可
*/

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.concurrent.atomic.AtomicInteger;

/**
* Java作为GUI(图形化用户界面)程序
* 1、入口必须继承Application抽象类
*/
public class FirstGUI2 extends Application {
   /**
    * atomicInteger：用于统计用户单击按钮的次数
    */
   private static AtomicInteger atomicInteger = new AtomicInteger(0);

   /**
    * 2、然后实现的它的start抽象方法
    *
    * @param primaryStage
    */
   @Override
   /**学习笔记
    * 必须通过start来override initialization时的动作
    */
   public void start(Stage primaryStage) {

       /**创建一个按钮控件
        * 并设置按钮上的文字，同时为他绑定单击事件，鼠标点击按钮后，控制台输出"Hello Friend"*/
       Button btn = new Button();
       btn.setText("Say 'Hello Friend'");
       btn.setOnAction(new EventHandler<ActionEvent>() {
           @Override
           public void handle(ActionEvent event) {
               System.out.println(atomicInteger.addAndGet(1) + "：Hello Friend");
           }
       });

       /**创建一个堆栈面板，面板包含控件*/
       StackPane root = new StackPane();
       root.getChildren().add(btn);

       /**创建一个场景，场景包含面板*/
       Scene scene = new Scene(root, 300, 250);

       /**最后将场景放入到舞台中，舞台包含场景<-场景包含面板<-面板包含控件*/
       primaryStage.setTitle("Hello World");
       primaryStage.setScene(scene);
       /**显示*/
       primaryStage.show();
   }
       /**学习笔记
        * Stage-->stackPane-->Controller-->Action of controller
        */
   public static void main(String[] args) {
       /**
        * GUI程序必须从入口的main方法进入并启动
        * launch是Application中的，调用它则可启动此GUI程序了
        */
       launch(args);
   }
}
