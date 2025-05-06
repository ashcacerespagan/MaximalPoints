module com.ashleycacerespagan.maximalpoints.maximalpoints {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    opens com.ashleycacerespagan.maximalpoints.maximalpoints to javafx.fxml;
    exports com.ashleycacerespagan.maximalpoints.maximalpoints;
}