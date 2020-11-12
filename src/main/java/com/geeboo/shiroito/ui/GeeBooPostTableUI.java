/*
 * Created by JFormDesigner on Tue Nov 10 16:25:45 CST 2020
 */

package com.geeboo.shiroito.ui;

import com.geeboo.shiroito.utils.GeeBooRequestUtils;
import com.geeboo.shiroito.utils.ParamValueUtils;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 * @author Brainrain
 */
public class GeeBooPostTableUI{

    private static GeeBooPostTableUI instance = new GeeBooPostTableUI();

    private GeeBooPostTableUI() {
        initComponents();
        initComBox();
    }



    public JLayeredPane getMainGeebooWindow() {
        return mainGeebooWindow;
    }

    public JPanel getParamFormPanel() {
        return paramFormPanel;
    }

    public JTextArea getResultArea() {
        return resultArea;
    }

    public JComboBox getEvmTypeComBox() {
        return evmTypeComBox;
    }

    public JComboBox getUserTypeComBox() {
        return userTypeComBox;
    }

    private void addParamEvent(ActionEvent e) {
        // TODO add your code here
        FormLayout formLayout = (FormLayout)paramFormPanel.getLayout();
        formLayout.appendRow(FormFactory.DEFAULT_ROWSPEC);
        formLayout.appendRow(FormFactory.LINE_GAP_ROWSPEC);
        int componentCount = paramFormPanel.getComponentCount();
        JPanel jPanel = newParamPanel("", "");
        CellConstraints cc = new CellConstraints();
        paramFormPanel.add(jPanel, cc.xy(1 ,1 + (componentCount) * 2));
    }

    private void postEvent(ActionEvent e) {
        // TODO add your code here

        resultArea.setText("");
        Thread thread = new Thread(() -> {
            Map<String, String> paramFormMap = getParamFormMap();
            Object evm = evmTypeComBox.getSelectedItem();
            Object userTye = userTypeComBox.getSelectedItem();
            String uri = urlLabel.getText();
            String result = GeeBooRequestUtils.post(paramFormMap, evm.toString(), userTye.toString(), uri);
            resultArea.setText(result);
        });

        thread.start();
    }

    public JLabel getUrlLabel() {
        return urlLabel;
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        mainGeebooWindow = new JLayeredPane();
        scrollPane1 = new JScrollPane();
        resultArea = new JTextArea();
        scrollPane2 = new JScrollPane();
        paramFormPanel = new JPanel();
        postB = new JButton();
        panel2 = new JPanel();
        label1 = new JLabel();
        userTypeComBox = new JComboBox();
        label2 = new JLabel();
        evmTypeComBox = new JComboBox();
        addParamB = new JButton();
        urlLabel = new JLabel();
        CellConstraints cc = new CellConstraints();

        //======== mainGeebooWindow ========
        {

            //======== scrollPane1 ========
            {

                //---- resultArea ----
                resultArea.setLineWrap(true);
                scrollPane1.setViewportView(resultArea);
            }
            mainGeebooWindow.add(scrollPane1, JLayeredPane.DEFAULT_LAYER);
            scrollPane1.setBounds(10, 450, 575, 295);

            //======== scrollPane2 ========
            {
                scrollPane2.setAutoscrolls(true);
                scrollPane2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

                //======== paramFormPanel ========
                {
                    paramFormPanel.setLayout(new FormLayout(
                        "221dlu",
                        ""));
                }
                scrollPane2.setViewportView(paramFormPanel);
            }
            mainGeebooWindow.add(scrollPane2, JLayeredPane.DEFAULT_LAYER);
            scrollPane2.setBounds(75, 90, 430, 300);

            //---- postB ----
            postB.setText("\u63d0\u4ea4");
            postB.addActionListener(e -> postEvent(e));
            mainGeebooWindow.add(postB, JLayeredPane.DEFAULT_LAYER);
            postB.setBounds(new Rectangle(new Point(300, 405), postB.getPreferredSize()));

            //======== panel2 ========
            {
                panel2.setLayout(new FormLayout(
                    new ColumnSpec[] {
                        new ColumnSpec(Sizes.dluX(40)),
                        FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
                        FormFactory.DEFAULT_COLSPEC,
                        FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
                        FormFactory.DEFAULT_COLSPEC,
                        FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
                        FormFactory.DEFAULT_COLSPEC,
                        FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
                        FormFactory.DEFAULT_COLSPEC,
                        FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
                        FormFactory.DEFAULT_COLSPEC,
                        FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
                        FormFactory.DEFAULT_COLSPEC,
                        FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
                        FormFactory.DEFAULT_COLSPEC
                    },
                    RowSpec.decodeSpecs("18dlu")));

                //---- label1 ----
                label1.setText("\u7528\u6237\u7c7b\u578b");
                panel2.add(label1, cc.xy(1, 1, CellConstraints.CENTER, CellConstraints.DEFAULT));
                panel2.add(userTypeComBox, cc.xy(3, 1));

                //---- label2 ----
                label2.setText("\u73af\u5883");
                panel2.add(label2, cc.xy(13, 1));
                panel2.add(evmTypeComBox, cc.xy(15, 1));
            }
            mainGeebooWindow.add(panel2, JLayeredPane.DEFAULT_LAYER);
            panel2.setBounds(65, 20, 460, 25);

            //---- addParamB ----
            addParamB.setText("\u6dfb\u52a0");
            addParamB.addActionListener(e -> addParamEvent(e));
            mainGeebooWindow.add(addParamB, JLayeredPane.DEFAULT_LAYER);
            addParamB.setBounds(new Rectangle(new Point(150, 405), addParamB.getPreferredSize()));
            mainGeebooWindow.add(urlLabel, JLayeredPane.DEFAULT_LAYER);
            urlLabel.setBounds(85, 50, 405, 35);
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JLayeredPane mainGeebooWindow;
    private JScrollPane scrollPane1;
    private JTextArea resultArea;
    private JScrollPane scrollPane2;
    private JPanel paramFormPanel;
    private JButton postB;
    private JPanel panel2;
    private JLabel label1;
    private JComboBox userTypeComBox;
    private JLabel label2;
    private JComboBox evmTypeComBox;
    private JButton addParamB;
    private JLabel urlLabel;
    // JFormDesigner - End of variables declaration  //GEN-END:variables



    private void removeParamEvent(ActionEvent e, JComponent jComponent) {
        paramFormPanel.remove(jComponent);

    }

    private void initComBox() {
        userTypeComBox.addItem("APP");
        userTypeComBox.addItem("OMS");

        evmTypeComBox.addItem("local");
        evmTypeComBox.addItem("dev");
        evmTypeComBox.addItem("test");
        evmTypeComBox.addItem("prod");
    }

    private JPanel newParamPanel(String param, String value) {
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new FormLayout(
                new ColumnSpec[] {
                        new ColumnSpec(Sizes.dluX(12)),
                        FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
                        FormFactory.DEFAULT_COLSPEC,
                        FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
                        new ColumnSpec(Sizes.dluX(73)),
                        FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
                        new ColumnSpec(Sizes.dluX(110))
                },
                RowSpec.decodeSpecs("default")));

        JButton jButton = new JButton("-");
        jButton.addActionListener(e -> removeParamEvent(e, jPanel));

        JCheckBox checkBox = new JCheckBox();
        checkBox.setSelected(true);
        JTextField paramText = new JTextField();
        JTextField valueText = new JTextField();
        paramText.setText(param);
        valueText.setText(value);
        CellConstraints cc = new CellConstraints();
        //---- button2 ----
        jPanel.add(jButton, cc.xy(1, 1));
        jPanel.add(checkBox, cc.xy(3, 1));
        jPanel.add(paramText, cc.xy(5, 1));
        jPanel.add(valueText, cc.xy(7, 1));
        return jPanel;
    }

    public static GeeBooPostTableUI getInstance() {
        return instance;
    }

    public void paramFormGenerate(Map<String, String> map, String url) {

        paramFormPanel.removeAll();
        resultArea.setText("");
        urlLabel.setText(url);

        int size = map.size();

        if(size == 0) {
            return;
        }

        FormLayout formLayout = (FormLayout)paramFormPanel.getLayout();
        for(int i = 0; i < size; i ++) {
            formLayout.appendRow(FormFactory.DEFAULT_ROWSPEC);
            formLayout.appendRow(FormFactory.LINE_GAP_ROWSPEC);
        }

        CellConstraints cc = new CellConstraints();

        Set<Map.Entry<String, String>> entries = map.entrySet();

        int index = 0;

        for(Map.Entry<String, String> entry: entries) {
            JPanel jPanel = newParamPanel(entry.getKey(), entry.getValue());
            paramFormPanel.add(jPanel, cc.xy(1 ,1 + index * 2));
            index ++;
        }


    }

    private Map<String, String> getParamFormMap() {
        int size = paramFormPanel.getComponentCount();
        Map<String, String> map = new HashMap<>();
        for(int i = 0; i < size; i++) {

            JPanel component = (JPanel)paramFormPanel.getComponent(i);
            JCheckBox checkBox = (JCheckBox)component.getComponent(1);
            JTextField paramText = (JTextField)component.getComponent(2);
            JTextField valueText = (JTextField)component.getComponent(3);

            if(!checkBox.isSelected()) {
                continue;
            }

            map.put(paramText.getText(), valueText.getText());
        }

        return map;
    }

}
