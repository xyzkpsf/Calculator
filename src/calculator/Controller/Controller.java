package calculator.Controller;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public class Controller {

    @FXML
    private Label topText;
    @FXML
    private Label bottomText;
    @FXML
    private double val = 0;
    @FXML
    private String operator;
    @FXML
    private boolean hasDot;
    @FXML
    private boolean resetBottom = true;
    @FXML
    private boolean restart = false;


    public double parseValue(String str) {
        double res;
        if (str.charAt(0) == '-') {
            res = - Double.parseDouble(str.substring(1, str.length()-1));
        } else {
            res = Double.parseDouble(str);
        }
        return res;
    }

    public void showBottomExpression(String inputText){
        if (restart) {
            topText.setText("");
            restart = false;
            val = 0;
        }
        if (resetBottom) {
            bottomText.setText(inputText);
            resetBottom = false;
        } else {
            bottomText.setText(bottomText.getText() + inputText);
        }
    }

    public void operate(String input) {
        double bottomVal = parseValue(bottomText.getText());
        String top = topText.getText();
        String bottom = bottomText.getText();
        if (top.isEmpty()) {
            topText.setText(bottom + " " + input);
            val = bottomVal;
            operator = input;
            resetBottom = true;
            hasDot = false;

        } else {
            if (operator == null) {
                topText.setText(top + " " + input);
                resetBottom = true;
                hasDot = false;
                val = bottomVal;
            } else {
                switch (operator) {
                    case "+" -> val += bottomVal;
                    case "-" -> val -= bottomVal;
                    case "*" -> val *= bottomVal;
                    case "/" -> {
                        if (bottomVal == 0) {
                            clear();
                            showBottomExpression("Cannot divide by zero");
                        }
                        val /= bottomVal;
                    }
                }
                topText.setText(top + " " + bottom + " " + input);
                bottomText.setText(Double.toString(val));
                operator = input;
                resetBottom = true;
                hasDot = false;
            }
        }
        if (input.compareTo("=") == 0) {
            restart = true;
        }
    }

    public void delete(){
        if (!bottomText.getText().isEmpty()) {
            String curr = bottomText.getText();
            bottomText.setText(curr.substring(0, curr.length() - 1));
        }
    }

    public void clear(){
        topText.setText("");
        bottomText.setText("");
        val = 0;
        hasDot = false;
        resetBottom = true;
        hasDot = false;
    }

    public void inputDot() {
        if (!hasDot) {
            showBottomExpression(".");
            hasDot = true;
        }
    }

    public void ln() {
        double bottomVal = parseValue(bottomText.getText());
        topText.setText("ln(" + bottomVal + ")");
        val = Math.log(bottomVal);
        bottomText.setText(String.format("%.15f", val));
    }

    public void log() {
        double bottomVal = parseValue(bottomText.getText());
        topText.setText("log(" + bottomVal + ")");
        val = Math.log10(bottomVal);
        bottomText.setText(String.format("%.15f", val));
    }

    public void tenPower(){
        double bottomVal = parseValue(bottomText.getText());
        topText.setText("10^(" + bottomVal + ")");
        val = Math.pow(10, bottomVal);
        bottomText.setText(Double.toString(val));
    }

    public void e(){
        val = Math.exp(1);
        bottomText.setText(Double.toString(val));
    }

    public void square() {
        double bottomVal = parseValue(bottomText.getText());
        topText.setText("sqr(" + bottomVal + ")");
        val = Math.pow(bottomVal, 2);
        bottomText.setText(Double.toString(val));
    }

    public void negate() {
        double bottomVal = parseValue(bottomText.getText());
        bottomText.setText(Double.toString(-bottomVal));
    }

    public void onMouseClicked(MouseEvent mouseEvent) {
        Button button = (Button) mouseEvent.getSource();
        String clicked = button.getText();

        switch (clicked) {
            case "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" -> showBottomExpression(clicked);
            case "." -> inputDot();
            case "/", "*", "-", "+", "=" -> operate(clicked);
            case "Delete" -> delete();
            case "Clear" -> bottomText.setText("");
            case "C" -> clear();
            case "ln" -> ln();
            case "log" -> log();
            case "10^x" -> tenPower();
            case "e" -> e();
            case "X^2" -> square();
            case "+/-" -> negate();
        }
    }

}
