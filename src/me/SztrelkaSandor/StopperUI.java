package me.SztrelkaSandor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Duration;
import java.time.LocalTime;
import java.util.TimerTask;
import java.util.Timer;

public class StopperUI extends JFrame implements ActionListener {
    JPanel mainPanel;
    JButton button_startStop, button_reszidoReset;
    JLabel label_time, label_reszido;
    Timer stopper;
    TimerTask task;
    int ora;
    int perc;
    int masodperc;
    int miliSecond;
    String oraToString = String.format("%02d", ora);
    String percToString = String.format("%02d", perc);
    String masodpercToString = String.format("%02d", masodperc);
    String miliSecondToString = String.format("%03d", miliSecond);
    LocalTime startTime;
    Duration elapsed, stopDuration;

    public StopperUI() {
        init();
    }

    private void init() {
        this.setTitle("Stopper");
        this.setSize(600, 400);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        mainPanel = (JPanel) (this.getContentPane());
        this.setLayout(null);
        stopDuration = Duration.ZERO;

        button_startStop = new JButton("Start");
        button_startStop.setBounds(20, 20, 125, 30);
        button_startStop.setFont(new Font("Courier new", Font.BOLD, 20));
        button_startStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopper = new Timer();

                if (button_startStop.getText().equals("Start")) {
                    button_startStop.setText("Stop");
                    button_reszidoReset.setText("Részidő");
                    startTime=LocalTime.now();

                    task = new TimerTask() {
                        @Override
                        public void run() {
                            elapsed = Duration.between(startTime, LocalTime.now());
                            elapsed = elapsed.plus(stopDuration);
                            ora = elapsed.toHoursPart();
                            perc = elapsed.toMinutesPart();
                            masodperc = elapsed.toSecondsPart();
                            miliSecond = elapsed.toMillisPart();
                            miliSecondToString = String.format("%03d", miliSecond);
                            masodpercToString = String.format("%02d", masodperc);
                            percToString = String.format("%02d", perc);
                            oraToString = String.format("%02d", ora);
                            label_time.setText(oraToString + ":" + percToString + ":" + masodpercToString + ":" + miliSecondToString);

                        }
                    };
                    stopper.schedule(task, 0, 1);
                } else if (button_startStop.getText().equals("Stop")) {
                    button_startStop.setText("Start");
                    button_reszidoReset.setText("Reset");
                    task.cancel();

                    Duration duration = Duration.between(startTime, LocalTime.now());
                    stopDuration = stopDuration.plus(duration);
                }


            }
        });

        button_reszidoReset = new JButton("Részidő");
        button_reszidoReset.setBounds(20, 60, 125, 30);
        button_reszidoReset.setFont(new Font("Courier new", Font.PLAIN, 20));
        button_reszidoReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (button_reszidoReset.getText().equals("Reset")) {
                    button_reszidoReset.setText("Részidő");
                    label_reszido.setText("");

                    miliSecond = 0;
                    masodperc = 0;
                    perc = 0;
                    ora = 0;

                    miliSecondToString = String.format("%03d", miliSecond);
                    masodpercToString = String.format("%02d", masodperc);
                    percToString = String.format("%02d", perc);
                    oraToString = String.format("%02d", ora);

                    label_time.setText(oraToString + ":" + percToString + ":" + masodpercToString + ":" + miliSecondToString);
                } else if ((button_reszidoReset.getText().equals("Részidő")) && (!label_time.getText().equals("00:00:00:000"))) {
                    label_reszido.setText(label_reszido.getText() + oraToString + ":" + percToString + ":" + masodpercToString + ":" + miliSecond + "  ");
                }
            }
        });


        label_time = new JLabel(oraToString + ":" + percToString + ":" + masodpercToString + ":" + miliSecondToString, SwingConstants.HORIZONTAL);
        label_time.setBounds(200, 30, 350, 60);
        label_time.setFont(new Font("Courier new", Font.ITALIC, 30));

        label_reszido = new JLabel("", SwingConstants.HORIZONTAL);
        label_reszido.setBounds(200, 100, 350, 200);
        label_reszido.setFont(new Font("Courier new", Font.ITALIC, 10));
        label_reszido.setBorder(BorderFactory.createLineBorder(Color.BLUE, 1));


        mainPanel.add(button_startStop);
        mainPanel.add(button_reszidoReset);
        mainPanel.add(label_time);
        mainPanel.add(label_reszido);

        this.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
