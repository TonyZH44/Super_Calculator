package com.example.supercalculator.ui.home;


import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.supercalculator.DataBaseCalculator;
import com.example.supercalculator.R;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.content.Context.CLIPBOARD_SERVICE;

public class CalculatorFragment extends Fragment {

    Button button0, button1, button2, button3, button4, button5, button6,
            button7, button8, button9, buttonAdd, buttonSub, buttonDivision,
            buttonMul, button10, buttonC, buttonEqual,  buttonInsert, buttonPercent, buttonSin, buttonCos, buttonTan, buttonExp;

    ImageButton buttonDelete;

    TextView EditText;
    ListView listView;
    ArrayAdapter historyAdapter;

    ClipData clipData;

    ClipboardManager clipboardManager;
    DataBaseCalculator dataBaseCalc;

    HashMap<String, Double> history_map;
    List<HashMap.Entry<String, Double>> history_list;


    String expression = "";

    Double valueOne = null;
    Double valueTwo = null;
    Double ans = null;



    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.calculator, container, false);

        clipboardManager = (ClipboardManager) getContext().getSystemService(CLIPBOARD_SERVICE);

        dataBaseCalc = new DataBaseCalculator(getContext());

        button0 = root.findViewById(R.id.button0);
        button1 = root.findViewById(R.id.button1);
        button2 = root.findViewById(R.id.button2);
        button3 = root.findViewById(R.id.button3);
        button4 = root.findViewById(R.id.button4);
        button5 = root.findViewById(R.id.button5);
        button6 = root.findViewById(R.id.button6);
        button7 = root.findViewById(R.id.button7);
        button8 = root.findViewById(R.id.button8);
        button9 = root.findViewById(R.id.button9);
        button10 = root.findViewById(R.id.button10);
        buttonAdd = root.findViewById(R.id.buttonadd);
        buttonSub = root.findViewById(R.id.buttonsub);
        buttonMul = root.findViewById(R.id.buttonmul);
        buttonDivision = root.findViewById(R.id.buttondiv);
        buttonC = root.findViewById(R.id.buttonC);
        buttonEqual = root.findViewById(R.id.buttoneql);
        EditText = root.findViewById(R.id.text1);
        listView = root.findViewById(R.id.list);
        buttonDelete = root.findViewById(R.id.buttondel);
        buttonInsert = root.findViewById(R.id.buttoninsert);
        buttonPercent = root.findViewById(R.id.buttonPercent);
        buttonSin = root.findViewById(R.id.buttonSin);
        buttonCos = root.findViewById(R.id.buttonCos);
        buttonTan = root.findViewById(R.id.buttonTan);
        buttonExp = root.findViewById(R.id.buttonExp);

        ShowHistoryOnListView(dataBaseCalc);

        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipData data = clipboardManager.getPrimaryClip();
                ClipData.Item item = null;
                if (data != null) {
                    item = data.getItemAt(0);
                    String text = item.getText().toString();
                    try {
                        Double test = Double.parseDouble(text);
                    } catch (NumberFormatException e) {
                        Toast.makeText(getContext(), "Not a Number", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    EditText.setText(EditText.getText() + text);
                    Toast.makeText(getContext(), "Inserted", Toast.LENGTH_SHORT).show();
                }
                ans = null;
            }

        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String b = EditText.getText().toString();

                if (b.isEmpty()){}
                else {
                    if(b.endsWith("(")){
                        b = b.substring(0, b.length()-1);

                        if (b.endsWith("n") || b.endsWith("s")){
                            b = b.substring(0, b.length()-3);
                        }
                        EditText.setText(b);

                    }else
                        EditText.setText(b.substring(0, b.length()-1));
                }
                ans = null;
            }
        });

        buttonSin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String b = EditText.getText().toString();
                if (specIFs(b)){
                    if (!hasAction(b) && endsWithANumber(b)){
                        EditText.setText(b + "*");
                    }
                    EditText.setText(EditText.getText()+ "sin(");
                    ans = null;
                }
            }
        });

        buttonCos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String b = EditText.getText().toString();
                if (specIFs(b)){
                    if (!hasAction(b) && endsWithANumber(b)){
                        EditText.setText(b + "*");
                    }
                    EditText.setText(EditText.getText()+ "cos(");
                    ans = null;
                }
            }
        });

        buttonTan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String b = EditText.getText().toString();
                if (specIFs(b)){
                    if (!hasAction(b) && endsWithANumber(b)){
                        EditText.setText(b + "*");
                    }
                    EditText.setText(EditText.getText()+ "tan(");
                    ans = null;
                }
            }
        });

        buttonExp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String b = EditText.getText().toString();
                if(b.isEmpty() || !Character.isDigit(b.charAt(b.length()-1))) {}
                else {
                    EditText.setText(EditText.getText() + "^(");
                    ans = null;
                }
            }
        });

        buttonPercent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String b = EditText.getText().toString();

                if(b.isEmpty() || !Character.isDigit(b.charAt(b.length()-1))) {}
                else {
                    String k = getLastNumber(b);
                    Double g = Double.parseDouble(k)/100;
                    EditText.setText(b.substring(0, b.length()-k.length()) + g);
                    ans = null;
                }
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ans != null) {
                    ans = null;
                    EditText.setText("1");
                }
                else EditText.setText(EditText.getText() + "1");
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ans != null) {
                    ans = null;
                    EditText.setText("2");
                } else EditText.setText(EditText.getText() + "2");
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ans != null) {
                    ans = null;
                    EditText.setText("3");
                } else EditText.setText(EditText.getText() + "3");
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ans != null) {
                    ans = null;
                    EditText.setText("4");
                } else EditText.setText(EditText.getText() + "4");
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ans != null) {
                    ans = null;
                    EditText.setText("5");
                } else EditText.setText(EditText.getText() + "5");
            }
        });

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ans != null) {
                    ans = null;
                    EditText.setText("6");
                } else EditText.setText(EditText.getText() + "6");
            }
        });

        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ans != null) {
                    ans = null;
                    EditText.setText("7");
                } else EditText.setText(EditText.getText() + "7");
            }
        });

        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ans != null) {
                    ans = null;
                    EditText.setText("8");
                } else EditText.setText(EditText.getText() + "8");
            }
        });

        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ans != null) {
                    ans = null;
                    EditText.setText("9");
                } else EditText.setText(EditText.getText() + "9");
            }
        });

        button0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ans != null) {
                    ans = null;
                    EditText.setText("0");
                } else EditText.setText(EditText.getText() + "0");
            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String b = EditText.getText().toString();
                if (b.isEmpty()) return;
                if (!hasAction(b))
                {
                    while (!allBracketsClosed(b)){
                        EditText.setText(EditText.getText().toString() + ")");
                        b = EditText.getText().toString();
                    }
                    EditText.setText(EditText.getText() + "+");
                    ans = null;
                }
            }
        });

        buttonSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String b = EditText.getText().toString();
                if (minusIFs(b))
                {
                    if (!b.endsWith("("))
                        while (!allBracketsClosed(b)) {
                            EditText.setText(EditText.getText().toString() + ")");
                            b = EditText.getText().toString();
                        }
                    EditText.setText(EditText.getText() + "-");
                    ans = null;
                }
            }
        });

        buttonMul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String b = EditText.getText().toString();
                if (b.isEmpty()) return;
                if (!hasAction(b))
                {
                    while (!allBracketsClosed(b)){
                        EditText.setText(EditText.getText().toString() + ")");
                        b = EditText.getText().toString();
                    }
                    EditText.setText(EditText.getText() + "*");
                    ans = null;
                }
            }
        });

        buttonDivision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String b = EditText.getText().toString();
                if (b.isEmpty()) return;
                if (!hasAction(b))
                {
                    while (!allBracketsClosed(b)){
                        EditText.setText(EditText.getText().toString() + ")");
                        b = EditText.getText().toString();
                    }
                    EditText.setText(EditText.getText() + "/");
                    ans = null;
                }
            }
        });

        buttonEqual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                expression = EditText.getText().toString();

                if (!expression.isEmpty() && hasNumber(expression)) {
                    int actIndex;

                    while (!allBracketsClosed(expression)){
                        EditText.setText(EditText.getText().toString() + ")");
                        expression = EditText.getText().toString();
                    }



                    String noBrackets = ignoreBrackets(expression);

                    if (!hasAction(noBrackets)){

                        String temp = expression;

                        if (specCheckBool(expression) || expression.contains("^")) {

                            if (!numberBtwnBrackets(expression)) return;

                            switch (specCheck(expression)) {
                                default:
                                    temp = multiplyItself(expression);
                                    break;
                                case "tan":
                                    temp = calcTan(expression);
                                    break;
                                case "cos":
                                    temp = calcCos(expression);
                                    break;
                                case "sin":
                                    temp = calcSin(expression);
                                    break;
                            }
                        }

                        ans = Double.parseDouble(temp);
                        EditText.setText(ans.toString());

                    } else {

                        if (noBrackets.startsWith("-"))
                            actIndex = noBrackets.substring(1).indexOf(getAction(noBrackets)) + 1;
                        else actIndex = noBrackets.indexOf(getAction(noBrackets));

                        String act = String.valueOf(expression.charAt(actIndex));

                        String firsthalf = expression.substring(0, actIndex);
                        String secondhalf = expression.substring(actIndex + 1);

                        if (!hasNumber(firsthalf)) return;
                        if (!hasNumber(secondhalf)) return;

                        String temp1;
                        String temp2;

                        switch (specCheck(firsthalf)) {
                            default:
                                temp1 = multiplyItself(firsthalf);
                                break;
                            case "tan":
                                temp1 = calcTan(firsthalf);
                                break;
                            case "cos":
                                temp1 = calcCos(firsthalf);
                                break;
                            case "sin":
                                temp1 = calcSin(firsthalf);
                                break;
                        }

                        switch (specCheck(secondhalf)) {
                            default:
                                temp2 = multiplyItself(secondhalf);
                                break;
                            case "tan":
                                temp2 = calcTan(secondhalf);
                                break;
                            case "cos":
                                temp2 = calcCos(secondhalf);
                                break;
                            case "sin":
                                temp2 = calcSin(secondhalf);
                                break;
                        }


                        valueOne = Double.parseDouble(temp1);
                        valueTwo = Double.parseDouble(temp2);


                        if (act.equals("+")) {
                            ans = valueOne + valueTwo;
                            EditText.setText(ans.toString());
                        }

                        if (act.equals("-")) {
                            ans = valueOne - valueTwo;
                            EditText.setText(ans.toString());
                        }

                        if (act.equals("*")) {
                            ans = valueOne * valueTwo;
                            EditText.setText(ans.toString());
                        }

                        if (act.equals("/")) {
                            ans = valueOne / valueTwo;
                            EditText.setText(ans.toString());
                        }
                    }

                    DataBaseCalculator dataBaseCalc = new DataBaseCalculator(getContext());
                    dataBaseCalc.add(expression, ans);
                    ShowHistoryOnListView(dataBaseCalc);
                }
            }
        });

        buttonC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText.setText("");
            }

        });

        buttonC.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                EditText.setText("");
                valueOne = null;
                valueTwo = null;
                ans = null;
                dataBaseCalc.clearAll();
                historyAdapter.clear();
                return true;
            }
        });

        button10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ans != null) {
                    ans = null;
                    EditText.setText(".");
                } else {
                    if (getLastNumber(EditText.getText().toString()).contains(".")) return;
                    EditText.setText(EditText.getText() + ".");
                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String b = listView.getItemAtPosition(position).toString();

                b = b.substring(b.indexOf("=")+1);

                if (!hasNumber(b)) return;

                clipData = ClipData.newPlainText("number", b);
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(getContext(), "Copied", Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }

    private boolean allBracketsClosed(String b){
        int openedBrackets = countOccurences(b, '(', 0);
        int closedBrackets = countOccurences(b, ')', 0);

        if (closedBrackets < openedBrackets) {
            return false;
        }
        return true;
    }

    private static int countOccurences(String someString, char searchedChar, int index) {
        if (index >= someString.length()) {
            return 0;
        }

        int count = someString.charAt(index) == searchedChar ? 1 : 0;
        return count + countOccurences(
                someString, searchedChar, index + 1);
    }

    private void ShowHistoryOnListView(DataBaseCalculator dataBaseCalc) {
        history_map = dataBaseCalc.getHistory();
        history_list = new ArrayList(history_map.entrySet());
        historyAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, history_list);
        listView.setAdapter(historyAdapter);
    }

    private boolean hasAction(String b){
        if (b.isEmpty()) return false;
        while (!allBracketsClosed(b)){
            b = b + ")";
        }
        String k = ignoreBrackets(b);
        if (k.contains("+")) return true;
        if (k.contains("/")) return true;
        if (k.contains("*")) return true;

        if (k.substring(1).contains("-")) return true;
        return false;
    }

    private String getAction(String b){
        if (hasAction(b)){
            if(b.contains("+")) return "+";
            if(b.contains("/")) return "/";
            if(b.contains("*")) return "*";
            if(b.substring(1).contains("-")) return "-";
        }
        return null;
    }

    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    private String multiplyItself(String b){
        if (b.contains("^")){

            Double base = Double.parseDouble(b.substring(0, b.indexOf("^")));
            String n = b.substring(b.indexOf("(")+ 1, b.lastIndexOf(")"));

            String g;

            switch (specCheck(n)){
                default:
                    g = n;
                    break;
                case "tan":
                    g = calcTan(n);
                    break;
                case "cos":
                    g = calcCos(n);
                    break;
                case "sin":
                    g = calcSin(n);
                    break;
            }

            String k = multiplyItself(g);

            Double temp = Double.parseDouble(k);

            Double ans = Math.pow(base, temp);
            return ans.toString();
        }
        return b;
    }

    private boolean checkBrackets(String b){
        if (b.contains("(") && b.contains(")")){
            return true;
        }
        return false;
    }

    private String ignoreBrackets(String k){
        while (checkBrackets(k)){
            int lastOp = k.lastIndexOf("(");
            int firstCl = k.substring(lastOp).indexOf(")")+lastOp;
            int n = firstCl - lastOp;
            k = k.substring(0, lastOp) + repeat("_", n) + k.substring(firstCl);

        }
        return k;
    }

    private String repeat(String p, int times){
        String k = "";
        for (int i = 0;i < times;i++){
            k = k + p;
        }
        return k;
    }

    private boolean minusIFs(String b){
        if (b.isEmpty()) return true;
        if (b.endsWith("-")) return false;
        if (b.contains("/") && !b.endsWith("/")) return false;
        if (b.contains("*") && !b.endsWith("*")) return false;
        while (!allBracketsClosed(b)){
            b = b + ")";
        }
        b = ignoreBrackets(b);
        if (b.substring(1).contains("-")) return false;
        if (b.contains("+")) return false;
        return true;
    }

    private boolean specIFs(String b){
        if (b.isEmpty()) return true;
        if (b.endsWith("(")) return true;
        if (b.contains("/") && !b.endsWith("/")) return false;
        if (b.contains("*") && !b.endsWith("*")) return false;
        return true;
    }

    private String calcSin(String b){
        if (b.startsWith("sin")){
            String base = b.substring(b.indexOf("(")+1, b.lastIndexOf(")"));

            String g;

            switch (specCheck(base)){
                default:
                    g = multiplyItself(base);
                    break;
                case "tan":
                    g = calcTan(base);
                    break;
                case "cos":
                    g = calcCos(base);
                    break;
                case "sin":
                    g = calcSin(base);
                    break;
            }


            Double temp = Double.parseDouble(g);

            temp = Math.sin(Math.toRadians(temp));
            temp = round(temp, 5);
            return temp.toString();
        }

        return b;
    }

    private String calcCos(String b){
        if (b.startsWith("cos")){
            String base = b.substring(b.indexOf("(")+1, b.lastIndexOf(")"));

            String g;

            switch (specCheck(base)){
                default:
                    g = multiplyItself(base);
                    break;
                case "tan":
                    g = calcTan(base);
                    break;
                case "cos":
                    g = calcCos(base);
                    break;
                case "sin":
                    g = calcSin(base);
                    break;
            }


            Double temp = Double.parseDouble(g);

            temp = Math.cos(Math.toRadians(temp));
            temp = round(temp, 5);
            return temp.toString();
        }

        return b;
    }

    private String calcTan(String b){
        if (b.startsWith("tan")){
            String base = b.substring(b.indexOf("(")+1, b.lastIndexOf(")"));

            String g;

            switch (specCheck(base)){
                default:
                    g = multiplyItself(base);
                    break;
                case "tan":
                    g = calcTan(base);
                    break;
                case "cos":
                    g = calcCos(base);
                    break;
                case "sin":
                    g = calcSin(base);
                    break;
            }


            Double temp = Double.parseDouble(g);

            temp = Math.tan(Math.toRadians(temp));
            temp = round(temp, 5);
            return temp.toString();
        }

        return b;
    }

    private String specCheck(String b){
        if(b.startsWith("sin")) return "sin";
        if(b.startsWith("cos")) return "cos";
        if(b.startsWith("tan")) return "tan";
        return "";
    }

    private boolean specCheckBool(String b){
        if(b.contains("sin")) return true;
        if(b.contains("cos")) return true;
        if(b.contains("tan")) return true;
        return false;
    }

    private boolean hasNumber(String b){
        Character symbol;

        for(int i = b.length()-1;i >= 0; i--){
            symbol = b.charAt(i);
            if(symbol.isDigit(b.charAt(i))){
                return true;
            }
        }
        return false;
    }

    private String getLastNumber(String b){

        int notNumbIndex = 0;
        String lastNumb;
        boolean success = false;
        Character symbol;

        for(int i = b.length()-1;i > 0; i--){
            symbol = b.charAt(i);
            if(!symbol.equals('.') && !symbol.isDigit(b.charAt(i))){
                notNumbIndex = i;
                success = true;
                break;
            }
        }
        if(success) lastNumb = b.substring(notNumbIndex+1);
        else lastNumb = b.substring(notNumbIndex);
        return lastNumb;
    }

    private boolean endsWithANumber(String b){
        Character symbol;

        if (hasNumber(b)){
            symbol = b.charAt(b.length()-1);
            if (symbol.isDigit(symbol)){
                return true;
            }
        }
        return false;
    }

    private boolean numberBtwnBrackets(String b){

            int lastOp = b.lastIndexOf("(");
            int firstCl = b.substring(lastOp).indexOf(")")+lastOp;
            if (firstCl - lastOp < 2) return false;
            String g = b.substring(lastOp, firstCl);
            if (hasNumber(g)){
                return true;
            }
            return false;
    }
}