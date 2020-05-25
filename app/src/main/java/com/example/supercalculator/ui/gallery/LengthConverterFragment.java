package com.example.supercalculator.ui.gallery;

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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.supercalculator.R;

import static android.content.Context.CLIPBOARD_SERVICE;

public class LengthConverterFragment extends Fragment {

    Button button0, button1, button2, button3, button4, button5, button6,
            button7, button8, button9, button10, buttonC, buttonEqual,  buttonInsert;

    ImageButton buttonDelete;

    String startValue, finalValue;

    TextView text1;
    TextView text2;

    ClipboardManager clipboardManager;
    ClipData clipData;




    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.converter, container, false);

        clipboardManager = (ClipboardManager) getContext().getSystemService(CLIPBOARD_SERVICE);


        //Spinner1
        Spinner spinner1 = root.findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(getContext(), R.array.length, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                startValue = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Spinner2
        Spinner spinner2 = root.findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getContext(), R.array.length, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                finalValue = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        text1 = root.findViewById(R.id.text1);
        text2 = root.findViewById(R.id.text2);

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
        buttonC = root.findViewById(R.id.buttonC);
        buttonEqual = root.findViewById(R.id.buttoneql);
        buttonDelete = root.findViewById(R.id.buttondel);
        buttonInsert = root.findViewById(R.id.buttoninsert);



        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String b = text1.getText().toString();

                if (b.isEmpty()){}
                else {
                    text1.setText(b.substring(0, b.length()-1));
                }
            }
        });



        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipData data = clipboardManager.getPrimaryClip();
                ClipData.Item item = null;
                if (data != null) {
                    item = data.getItemAt(0);
                    String text = item.getText().toString();

                    try {
                        Float test = Float.parseFloat(text);
                    } catch (NumberFormatException e) {
                        Toast.makeText(getContext(), "Not a Number", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    text1.setText(text);
                }

            }

        });


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text1.setText(text1.getText() + "1");
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text1.setText(text1.getText() + "2");
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text1.setText(text1.getText() + "3");
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text1.setText(text1.getText() + "4");
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text1.setText(text1.getText() + "5");
            }
        });

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text1.setText(text1.getText() + "6");
            }
        });

        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text1.setText(text1.getText() + "7");
            }
        });

        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text1.setText(text1.getText() + "8");
            }
        });

        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text1.setText(text1.getText() + "9");
            }
        });

        button0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text1.setText(text1.getText() + "0");
            }
        });

        buttonC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text1.setText("");
                text2.setText("");
            }
        });

        button10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getLastNumber(text1.getText().toString()).contains(".")) return;
                text1.setText(text1.getText() + ".");
            }
        });

        buttonEqual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String b = text1.getText().toString();

                if (b.equals("")) {
                    Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();

                } else
                text2.setText(convertLength(Float.parseFloat(text1.getText().toString())));
            }
        });

        text2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String b = text2.getText().toString();

                clipData = ClipData.newPlainText("number", b);
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(getContext(), "Copied", Toast.LENGTH_SHORT).show();
            }
        });

        text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String b = text1.getText().toString();

                clipData = ClipData.newPlainText("number", b);
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(getContext(), "Copied", Toast.LENGTH_SHORT).show();
            }
        });


        return root;
    }

    private String convertLength(Float n) {
        switch (startValue){
            case "Nanometres":
                n = n/1000000000;
                break;
            case "Micrometres":
                n = n/1000000;
                break;
            case "Centimetres":
                n = n/100;
                break;
            case "Decimetres":
                n = n/10;
                break;
            case "Kilometres":
                n = n*1000;
                break;
            case "Millimetres":
                n = n/1000;
                break;
            default:
                break;
        }

        switch (finalValue){
            case "Nanometres":
                n = n*1000000000;
                break;
            case "Micrometres":
                n = n*1000000;
                break;
            case "Centimetres":
                n = n*100;
                break;
            case "Decimetres":
                n = n*10;
                break;
            case "Kilometres":
                n = n/1000;
                break;
            case "Millimetres":
                n = n*1000;
                break;
            default:
                break;
        }
        return n.toString();
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

}