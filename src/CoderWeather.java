


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Component;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Timer;
import java.util.TimerTask;

public class CoderWeather  extends JFrame implements ActionListener {

    SimpleDateFormat str = new SimpleDateFormat("dd/MM/yy");
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    Date bugun = new Date();
    private JComboBox<String> cmb;
    private final JRadioButton rbtn1 = new JRadioButton();
    private JLabel sonuc, bolge, bolgesnc, durum, durumsnc, derece, derecesnc,
            EnDusukSicakliksnc, peryodsnc, clocklbl, realclock, conn, label, lblPeryod, lblEnDkScaklk;

    private final String localweather = "/images/wallpaper.gif";
    private final String Clouded = "/images/Clouded.gif";
    private final String Rain = "/images/Rain.gif";
    private final String Snows = "/images/Snows.gif";
    private final String Sunny = "/images/Sunny.gif";
    private final String Sunny_Clouded = "/images/Sunny-Clouded.gif";
    private String Hour, Min, Sec;

    private Element eElement;
    public String[] dizi;
    private int x = 0;

    public CoderWeather() {
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);
        label = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource(localweather))));
        setContentPane(label);
        setFont(new Font("Cambria", Font.BOLD, 13));
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/icon.png")));
        setTitle("LIVE WEATHER");
        setResizable(false);
        init();

    }

    private void init() {
        dizi = new String[]{"ISTANBUL", "BURSA", "IZMIR", "A.KARAHISAR", "ADANA", "ANTALYA", "ANKARA", "KONYA", "BOLU",
            "ZONGULDAK", "SAMSUN", "TRABZON", "ERZURUM", "ELAZIĞ", "DIYARBAKIR", "GAZIANTEP", "EDİRNE", "MUĞLA",
            "BURDUR", "ESKİŞEHİR", "SİNOP", "ARTVİN", "ARDAHAN", "ADIYAMAN", "KIRKLARELI", "MANISA", "HATAY",
            "CANKIRI", "DÜZCE", "AMASYA", "SIIRT", "KARS"};

        cmb = new JComboBox<>();
        cmb.setMinimumSize(new Dimension(28, 17));
        cmb.setEditable(true);
        cmb.setBackground(Color.WHITE);
        cmb.setLocation(150, 12);
        cmb.setSize(190, 28);

        cmb.addActionListener((ActionListener) this);
        cmb.addItem(null);
        for (String x : dizi) {
            cmb.addItem(x);
        }

        sonuc = new JLabel("  Select city name :");
        sonuc.setMaximumSize(new Dimension(120, 14));
        sonuc.setFont(new Font("Verdana", Font.BOLD, 11));
        sonuc.setForeground(Color.WHITE);
        sonuc.setLocation(10, 12);
        sonuc.setSize(125, 32);
        sonuc.setVisible(true);

        bolge = new JLabel("  Area          :");
        bolge.setFont(new Font("Verdana", Font.BOLD, 11));
        bolge.setForeground(Color.WHITE);
        bolge.setBounds(10, 60, 110, 32);

        bolgesnc = new JLabel();
        bolgesnc.setForeground(Color.WHITE);
        bolgesnc.setFont(new Font("Verdana", Font.BOLD, 11));
        bolgesnc.setBounds(150, 70, 250, 15);

        durum = new JLabel("  Status       :");
        durum.setFont(new Font("Verdana", Font.BOLD, 11));
        durum.setForeground(Color.WHITE);
        durum.setBounds(10, 253, 102, 20);

        durumsnc = new JLabel();
        durumsnc.setForeground(Color.WHITE.brighter());
        durumsnc.setFont(new Font("Verdana", Font.BOLD, 11));
        durumsnc.setBounds(150, 235, 250, 63);

        derece = new JLabel("  Max Temp :");
        derece.setFont(new Font("Verdana", Font.BOLD, 11));
        derece.setForeground(new Color(253, 184, 19));
        derece.setBounds(10, 195, 102, 32);

        derecesnc = new JLabel();
        derecesnc.setForeground(Color.WHITE.brighter());
        derecesnc.setFont(new Font("Verdana", Font.BOLD, 11));
        derecesnc.setBounds(150, 205, 250, 15);

        lblPeryod = new JLabel("  Peryod      :");
        lblPeryod.setFont(new Font("Verdana", Font.BOLD, 11));
        lblPeryod.setForeground(Color.WHITE);
        lblPeryod.setBounds(10, 101, 102, 32);

        lblEnDkScaklk = new JLabel("  Min Temp  :");
        lblEnDkScaklk.setFont(new Font("Verdana", Font.BOLD, 11));
        lblEnDkScaklk.setForeground(Color.CYAN);
        lblEnDkScaklk.setBounds(10, 148, 102, 32);

        EnDusukSicakliksnc = new JLabel("");
        EnDusukSicakliksnc.setForeground(Color.WHITE.brighter());
        EnDusukSicakliksnc.setFont(new Font("Verdana", Font.BOLD, 11));
        EnDusukSicakliksnc.setBounds(150, 158, 270, 15);

        peryodsnc = new JLabel("");
        peryodsnc.setForeground(Color.WHITE.brighter());
        peryodsnc.setFont(new Font("Verdana", Font.BOLD, 11));
        peryodsnc.setBounds(150, 106, 250, 15);

        conn = new JLabel(" Status : checking... ");
        conn.setFont(new Font("Microsoft New Tai Lue", Font.PLAIN, 11));
        conn.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        conn.setForeground(Color.WHITE);
        conn.setBackground(new Color(255, 255, 255));
        conn.setBounds(0, 370, 345, 20);

        getContentPane().add(sonuc);
        getContentPane().add(cmb);
        getContentPane().add(bolge);
        getContentPane().add(bolgesnc);
        getContentPane().add(derece);
        getContentPane().add(derecesnc);
        getContentPane().add(lblPeryod);
        getContentPane().add(lblEnDkScaklk);
        getContentPane().add(EnDusukSicakliksnc);
        getContentPane().add(peryodsnc);
        getContentPane().add(durum);
        getContentPane().add(durumsnc);
        getContentPane().add(conn);

        rbtn1.setAlignmentX(Component.CENTER_ALIGNMENT);
        rbtn1.setBounds(555, 10, 65, 23);
        getContentPane().add(rbtn1);
        rbtn1.addItemListener((ItemEvent arg0) -> {
            if (rbtn1.isSelected() == true) {
                JOptionPane.showMessageDialog(null,
                        "                            Developed by Onur Işık"
                        + "\nIn this application live weather datas getting from meteorology"
                        + "\nofficial site 'http://www.mgm.gov.tr' "
                        + "\n------------------------------------------------------------------------------------"
                        + "\n<Live Weather>  Copyright (C) <2016> <Coder ACJHP>"
                        + "\nThis program is free software: you can redistribute it and/or modify"
                        + "\nit under the terms of the GNU General Public License as published by"
                        + "\nthe Free Software Foundation, either version 3 of the License, or"
                        + "\n(at your option) any later version."
                        + "\nThis program is distributed in the hope that it will be useful,"
                        + "\nbut WITHOUT ANY WARRANTY; without even the implied warranty of"
                        + "\nMERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the"
                        + "\nGNU General Public License for more details."
                        + "\nYou should have received a copy of the GNU General Public License"
                        + "\nalong with this program.  If not, see <http://www.gnu.org/licenses/>."
                        + "\nAlso add information on how to contact you by electronic and paper mail.");
            }
        });

        clocklbl = new JLabel("Time Now :");
        clocklbl.setForeground(Color.BLACK.brighter());
        clocklbl.setBounds(88, 312, 167, 25);
        clocklbl.setFont(new Font("Lucida Grande", Font.BOLD, 27));
        getContentPane().add(clocklbl);

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {

            @SuppressWarnings("unused")
            @Override
            public void run() {

                GregorianCalendar now = new GregorianCalendar();
                Hour = (now.get(Calendar.HOUR_OF_DAY) + " :");
                Min = (now.get(Calendar.MINUTE) + " :");
                Sec = ("" + now.get(Calendar.SECOND));
                realclock.setText(Hour + " " + Min + " " + Sec);

                if (Calendar.HOUR_OF_DAY >= 12) {
                    realclock.setText(Hour + " " + Min + " " + Sec + "am");
                } else {
                    realclock.setText(Hour + " " + Min + " " + Sec + "pm");
                }
            }
        };
        timer.scheduleAtFixedRate(task, 0, 1000);

        realclock = new JLabel();
        realclock.setBounds(255, 312, 190, 25);
        realclock.setForeground(Color.WHITE);
        realclock.setFont(new Font("Lucida Grande", Font.BOLD, 27));
        getContentPane().add(realclock);
    }

    private void changeImage(String File) {

       label.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource(File))));
    }

    private void Clear() {
        bolgesnc.setText(" ");
        peryodsnc.setText(" ");
        durumsnc.setText(" ");
        derecesnc.setText(" ");
        EnDusukSicakliksnc.setText(" ");

    }

    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {

                    CoderWeather run = new CoderWeather();
                    run.setVisible(true);
                    run.setDefaultCloseOperation(EXIT_ON_CLOSE);
                    run.setSize(590, 425);
                    run.setResizable(false);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private static Document loadTestDocument(String url) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        return factory.newDocumentBuilder().parse(new URL(url).openStream());
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == cmb) {

            try {
                conn.setText(" Status : connected ");
                conn.setForeground(Color.GREEN.brighter());
                cmb.setBackground(Color.WHITE);
                Document doc = loadTestDocument("http://www.mgm.gov.tr/ftpdata/analiz/sonsoa.xml");
                doc.getDocumentElement().normalize();
                NodeList nList = doc.getElementsByTagName("SOA");

                for (int i = 0; i < nList.getLength(); i++) {
                    Node nNode = nList.item(i);

                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    }

                    eElement = (Element) nNode;
                    if (cmb.getSelectedIndex() >= 1) {
                        x = cmb.getSelectedIndex() - 1;
                        bolgesnc.setText(eElement.getElementsByTagName("Bolge").item(x).getTextContent());
                        peryodsnc.setText(eElement.getElementsByTagName("Peryot").item(x).getTextContent() + "  " + str.format(bugun));
                        durumsnc.setText("<html>" + eElement.getElementsByTagName("Durum").item(x).getTextContent() + "</html>");
                        derecesnc.setText(eElement.getElementsByTagName("Mak").item(x).getTextContent() + "°" + " C");
                        EnDusukSicakliksnc.setText(eElement.getElementsByTagName("Min").item(x).getTextContent() + "°" + " C");
                        
                         if (durumsnc.getText().toLowerCase().contains("açık")||durumsnc.getText().toLowerCase().contains("güneşli")) {
                            changeImage(Sunny);
                        } else if (durumsnc.getText().toLowerCase().contains("az bulutlu")) {
                            changeImage(Sunny_Clouded);
                        }else if (durumsnc.getText().toLowerCase().contains("sağanak yağışlı")) {
                            changeImage(Rain);
                        }else if(durumsnc.getText().toLowerCase().contains("çok bulutlu")){
                               changeImage(Clouded); 
                        }else if (durumsnc.getText().toLowerCase().contains("karlı")) {
                            changeImage(Snows);
                        }
                    } else {
                        Clear();
                        changeImage(localweather);
                    }

                }
            } catch (IOException ex) {
                conn.setText(" Status : disconnected. Please check your internet connection !!");
                conn.setForeground(new Color(255, 0, 0));
                cmb.setBackground(Color.RED);
            } catch (Exception ex) {
                Logger.getLogger(CoderWeather.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

}

