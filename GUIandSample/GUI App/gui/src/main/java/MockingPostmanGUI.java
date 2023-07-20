import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MockingPostmanGUI {
    private JFrame form;
    private JLabel ipLabel;
    private JTextField ipTextField;
    private JTextField pathTextField;
    private JTextArea jsonReqTextField;
    private JTextArea jsonResTextField;
    private JButton kirimButton;
    private JButton hapusButton;
    private JButton trxBaruButton;
    private JComboBox<String> dropDown;
    private JScrollPane scrollPane1;
    private JScrollPane scrollPane2;

    private String ipValue;
    private String pathValue;
    private String requestValue;
    private String responeValue;
    final private String reqPlaceHolder = "{\n" +"    \"name\": \"{name}\",\n" +
            "    \"phoneNumber\": \"{number}\"\n" +"}";

    public void createAndShowGUI() {
        form = new JFrame("REST API GUI");
        form.setSize(800,670);
        form.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        form.setLocationRelativeTo(null);
        form.setLayout(null);

        ipLabel = new JLabel("IP");
        ipLabel.setBounds(36,10,60,30);
        form.add(ipLabel);

        ipTextField = new JTextField();
        ipTextField.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        ipTextField.setBounds(106,10,290,30);
        ipTextField.setText("http://localhost:8080/contact_webapp_war");
        form.add(ipTextField);

        pathTextField = new JTextField();
        pathTextField.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        pathTextField.setBounds(406,10,240,30);
        form.add(pathTextField);


        jsonReqTextField = new JTextArea();
        jsonReqTextField.setLineWrap(true);

        scrollPane2 = new JScrollPane(jsonReqTextField); // Add the JTextArea to JScrollPane
        scrollPane2.setBounds(16,60,280,555);
        scrollPane2.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        form.add(scrollPane2);

        jsonResTextField = new JTextArea();
        jsonResTextField.setLineWrap(true);

        scrollPane1 = new JScrollPane(jsonResTextField); // Add the JTextArea to JScrollPane
        scrollPane1.setBounds(306,60,459,190);
        scrollPane1.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        form.add(scrollPane1);

        kirimButton = new JButton("Kirim");
        kirimButton.setBounds(306,470,100,30);
        kirimButton.addActionListener(e -> {
            if (ipTextField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(form, "IP cannot be empty.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                ipTextField.requestFocusInWindow();
            } else if (pathTextField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(form, "Path cannot be empty.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                pathTextField.requestFocusInWindow();
            } else if (jsonReqTextField.getText().isEmpty() && dropDown.getSelectedItem().equals("Create contact")) {
                JOptionPane.showMessageDialog(form, "Request cannot be empty.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                jsonReqTextField.requestFocusInWindow();
            } else if (pathTextField.getText().equals("/contact/{id}")) {
                JOptionPane.showMessageDialog(form, "Please Change the id bracket. Search for ID contact in 'Call all contact'", "Validation Error", JOptionPane.WARNING_MESSAGE);
                jsonReqTextField.requestFocusInWindow();
            } else if (jsonReqTextField.getText().equals(reqPlaceHolder)) {
                JOptionPane.showMessageDialog(form, "Please Change the name and phoneNumber bracket.", "Validation Error", JOptionPane.WARNING_MESSAGE);
                jsonReqTextField.requestFocusInWindow();
            }else if (!ipTextField.getText().isEmpty() && !pathTextField.getText().isEmpty()){
                ipValue = ipTextField.getText();
                pathValue = pathTextField.getText();
                requestValue = jsonReqTextField.getText();
                jsonResTextField.setText("");
                jsonReqTextField.setEnabled(true);
                System.out.println("Value from ipValue: " + ipValue);
                System.out.println("Value from pathValue: " + pathValue);
                System.out.println("Value from requestValue: " + requestValue);
                switch (dropDown.getSelectedItem().toString()){
                    case "Create contact" :
                        jsonResTextField.setText(beautifyJson(postService((ipValue + pathValue),requestValue,"POST")));
                        break;
                    case "Update a contact" :
                        jsonResTextField.setText(beautifyJson(postService((ipValue + pathValue),requestValue,"PUT")));
                        break;
                    case "Delete a contact" :
                        deleteService((ipValue + pathValue));
                        break;
                    case "Call all contact", "Call a contact":
                        jsonResTextField.setText(beautifyJson(getService(ipValue + pathValue)));
                        break;
                }
            }
        });
        form.add(kirimButton);

        hapusButton = new JButton("Hapus");
        hapusButton.setBounds(464,470,100,30);
        hapusButton.addActionListener(e->{
            ipTextField.setText("");
            pathTextField.setText("");
            jsonReqTextField.setText("");
            jsonResTextField.setText("");
            dropDown.setSelectedIndex(0);

        });
        form.add(hapusButton);

        String[] dropDownItems = {
                "Select a Method",
                "Create contact",
                "Call all contact",
                "Call a contact",
                "Update a contact",
                "Delete a contact"};
        dropDown = new JComboBox<>(dropDownItems);
        dropDown.setBounds(306,530,150,30);
        form.add(dropDown);

        trxBaruButton = new JButton("Trx Baru");
        trxBaruButton.setBounds(306,580,100,30);
        trxBaruButton.addActionListener(e->{
            ipTextField.setText("http://localhost:8080/contact_webapp_war");
            pathTextField.setText("");
            jsonReqTextField.setText("");
            jsonResTextField.setText("");
            dropDown.setSelectedIndex(0);

        });
        form.add(trxBaruButton);

        dropDown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the selected item from the JComboBox
                String selectedItem = (String) dropDown.getSelectedItem();
                selectedItem = selectedItem == null ? "" : (String) dropDown.getSelectedItem();
                switch(selectedItem){
                    case "Create contact":
                        pathTextField.setText("/contact");
                        jsonReqTextField.setText(reqPlaceHolder);
                        jsonReqTextField.setEnabled(true);
                        jsonResTextField.setText("");
                        break;
                    case "Call all contact":
                        pathTextField.setText("/contact/all");
                        jsonReqTextField.setEnabled(false);
                        jsonReqTextField.setText("");
                        jsonResTextField.setText("");
                        break;
                    case "Update a contact":
                        pathTextField.setText("/contact/{id}");
                        jsonReqTextField.setEnabled(true);
                        jsonReqTextField.setText(reqPlaceHolder);
                        jsonResTextField.setText("");
                        break;
                    case "Call a contact","Delete a contact":
                        pathTextField.setText("/contact/{id}");
                        jsonReqTextField.setEnabled(false);
                        jsonReqTextField.setText("");
                        jsonResTextField.setText("");
                        break;
                }
            }
        });
        dropDown.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    String selectedOption = (String) dropDown.getSelectedItem();
                    if (selectedOption.equals(dropDown.getItemAt(0))) {
                        dropDown.setSelectedIndex(-1); // Deselect the placeholder item
                    }
                }
            }
        });

        form.setVisible(true);
    }
    public static String beautifyJson(String json) {
        int indentationLevel = 0;
        StringBuilder beautifiedJson = new StringBuilder();

        for (char c : json.toCharArray()) {
            if (c == '{' || c == '[') {
                beautifiedJson.append(c).append('\n');
                indentationLevel++;
                appendIndentation(beautifiedJson, indentationLevel);
            } else if (c == '}' || c == ']') {
                beautifiedJson.append('\n');
                indentationLevel--;
                appendIndentation(beautifiedJson, indentationLevel);
                beautifiedJson.append(c);
            } else if (c == ',') {
                beautifiedJson.append(c).append('\n');
                appendIndentation(beautifiedJson, indentationLevel);
            } else {
                beautifiedJson.append(c);
            }
        }

        return beautifiedJson.toString();
    }

    private static void appendIndentation(StringBuilder stringBuilder, int indentationLevel) {
        for (int i = 0; i < indentationLevel; i++) {
            stringBuilder.append("    "); // Four spaces for each indentation level (adjust as needed)
        }
    }
    private String postService(String link, String object,String requestMethod){
        String result = "";
        try {
            result = Helper.postService(link,object,requestMethod);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    private String getService(String link){
        String result = "";
        try {
            result = Helper.getService(link);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return result;
    }
    private void deleteService(String link){
        try {
            Helper.deleteService(link);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }


}
