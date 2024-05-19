import javax.swing.*;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.IOException;

public class Main {

    public static String SMSsender(String to, String msg) throws IOException {
        String ApiKey = "567U0MDQ4ODcxMTcxNTk0OT4565gfhcx";
        String SenderID = "88096XXXXXX";
        URL url = new URL("https://sms.example.com/api/sms?ApiKey="+ApiKey+"&SenderID="+SenderID+"&number="+to+"&sms="+java.net.URLEncoder.encode(msg));
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        int responseCode = connection.getResponseCode();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = reader.readLine()) != null) {
            response.append(inputLine);
        }
        reader.close();

        return response.toString();
    }



    public static void main(String[] args) throws IOException{
        JFrame frame = new JFrame("SMS Sender");
        frame.setSize(900, 500);
        frame.setLocation(200, 150);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel headlineLabel = new JLabel("SMS Sender");
        headlineLabel.setFont(new Font("Arial", Font.BOLD, 24));
        frame.add(headlineLabel);
        headlineLabel.setBounds(380, 10, 500, 30);

        JTextField textField1 = new JTextField();
        JLabel label_textField1 = new JLabel("Enter Mobile Number:");
        JTextArea textArea = new JTextArea();
        JLabel label_textArea = new JLabel("Type your SMS:");
        JButton SendButton = new JButton("Send SMS");
        JLabel StatusMessage = new JLabel();

        SendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final String previousButtonText = SendButton.getText();
                SendButton.setText("Please Wait...");
                SwingUtilities.invokeLater(() -> {

                String phone = textField1.getText();
                String sms = textArea.getText();

                    try {
                        String res = SMSsender(phone, sms);
//                        String[] keyValuePairs = res.substring(1, res.length() - 1).split(",");
//                        String[] keyValue = keyValuePairs[1].split(":");
//                        String resmsg = keyValue[1].replaceAll("\"", "").trim();
                        StatusMessage.setText(res);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    } finally {
                        SendButton.setText(previousButtonText);
                        textField1.setText("");
                        textArea.setText("");
                    }

                });
            }
        });


        frame.add(label_textField1);
        frame.add(textField1);
        frame.add(label_textArea);
        frame.add(textArea);
        frame.add(SendButton);
        frame.add(StatusMessage);

        label_textField1.setBounds(250, 100, 400, 30);
        textField1.setBounds(250, 130, 400, 30);
        label_textArea.setBounds(250, 170, 400, 30);
        textArea.setBounds(250, 200, 400, 100);
        SendButton.setBounds(340, 310, 200, 50);
        StatusMessage.setBounds(340, 50, 800, 50);

        frame.setLayout(null);
        frame.setVisible(true);
    }
}
