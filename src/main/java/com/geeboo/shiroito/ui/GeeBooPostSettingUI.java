/*
 * Created by JFormDesigner on Mon Nov 09 16:19:00 CST 2020
 */

package com.geeboo.shiroito.ui;

import java.awt.*;
import java.awt.event.*;
import com.geeboo.shiroito.storage.GeeBooPostStorage;
import com.geeboo.shiroito.storage.GeeBooPostStorageService;
import com.geeboo.shiroito.storage.StorageKey;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.*;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.util.Map;

/**
 * @author Brainrain
 */
public class GeeBooPostSettingUI{
    public GeeBooPostSettingUI() {
        initComponents();
        addJComboBox();
    }

    private void evmChangeEvent(ItemEvent event) {

        if(event.getStateChange() != ItemEvent.SELECTED){
            return;
        }


        String item = (String)event.getItem();
        GeeBooPostStorage state = GeeBooPostStorageService.getInstance().getState();

        Map<String, Map<String, String>> postSetting = state.getPostSetting();
        Map<String, String> stringStringMap = postSetting.get(item);
        if(stringStringMap == null) {
            return;
        }

        final String domain = stringStringMap.getOrDefault(StorageKey.DOMAIN, "");
        final String secret = stringStringMap.getOrDefault(StorageKey.SECRET, "");
        final String omsUser = stringStringMap.getOrDefault(StorageKey.OMS_USER, "");
        final String omsPass = stringStringMap.getOrDefault(StorageKey.OMS_PASS, "");
        final String appUser = stringStringMap.getOrDefault(StorageKey.APP_USER, "");
        final String appPass = stringStringMap.getOrDefault(StorageKey.APP_PASS, "");

        this.domain.setText(domain);
        this.secret.setText(secret);
        this.omsUser.setText(omsUser);
        this.omsPass.setText(omsPass);
        this.appUser.setText(appUser);
        this.appUser.setText(appUser);
        this.appPass.setText(appPass);

    }

    private void tokenClearEvent(ActionEvent e) {
        // TODO add your code here
        GeeBooPostStorage state = GeeBooPostStorageService.getInstance().getState();
        Map<String, String> tokenMap = state.getTokenMap();
        String evmS = evm.getSelectedItem().toString();
        tokenMap.remove(StorageKey.getTokenKey("APP", evmS));
        tokenMap.remove(StorageKey.getTokenKey("OMS", evmS));
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        layeredPane1 = new JLayeredPane();
        evm = new JComboBox();
        label4 = new JLabel();
        panel1 = new JPanel();
        label6 = new JLabel();
        domain = new JTextField();
        label7 = new JLabel();
        secret = new JTextField();
        label8 = new JLabel();
        omsUser = new JTextField();
        label9 = new JLabel();
        omsPass = new JTextField();
        label10 = new JLabel();
        appUser = new JTextField();
        label11 = new JLabel();
        appPass = new JTextField();
        button1 = new JButton();
        CellConstraints cc = new CellConstraints();

        //======== layeredPane1 ========
        {

            //---- evm ----
            evm.addItemListener(e -> evmChangeEvent(e));
            layeredPane1.add(evm, JLayeredPane.DEFAULT_LAYER);
            evm.setBounds(285, 25, 135, evm.getPreferredSize().height);

            //---- label4 ----
            label4.setText("   \u73af\u5883");
            layeredPane1.add(label4, JLayeredPane.DEFAULT_LAYER);
            label4.setBounds(205, 25, 75, 27);

            //======== panel1 ========
            {
                panel1.setLayout(new FormLayout(
                    new ColumnSpec[] {
                        new ColumnSpec(Sizes.dluX(26)),
                        new ColumnSpec(Sizes.dluX(45)),
                        new ColumnSpec(Sizes.dluX(201)),
                        FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
                        FormFactory.DEFAULT_COLSPEC
                    },
                    new RowSpec[] {
                        new RowSpec(Sizes.dluY(24)),
                        FormFactory.LINE_GAP_ROWSPEC,
                        FormFactory.DEFAULT_ROWSPEC,
                        FormFactory.LINE_GAP_ROWSPEC,
                        FormFactory.DEFAULT_ROWSPEC,
                        FormFactory.LINE_GAP_ROWSPEC,
                        FormFactory.DEFAULT_ROWSPEC,
                        FormFactory.LINE_GAP_ROWSPEC,
                        FormFactory.DEFAULT_ROWSPEC,
                        FormFactory.LINE_GAP_ROWSPEC,
                        FormFactory.DEFAULT_ROWSPEC,
                        FormFactory.LINE_GAP_ROWSPEC,
                        FormFactory.DEFAULT_ROWSPEC,
                        FormFactory.LINE_GAP_ROWSPEC,
                        FormFactory.DEFAULT_ROWSPEC
                    }));

                //---- label6 ----
                label6.setText("domain");
                panel1.add(label6, cc.xy(2, 1, CellConstraints.CENTER, CellConstraints.DEFAULT));
                panel1.add(domain, cc.xy(3, 1));

                //---- label7 ----
                label7.setText("secret");
                panel1.add(label7, cc.xy(2, 3, CellConstraints.CENTER, CellConstraints.DEFAULT));
                panel1.add(secret, cc.xy(3, 3));

                //---- label8 ----
                label8.setText("oms-user");
                panel1.add(label8, cc.xy(2, 5, CellConstraints.CENTER, CellConstraints.DEFAULT));
                panel1.add(omsUser, cc.xy(3, 5));

                //---- label9 ----
                label9.setText("oms-pass");
                panel1.add(label9, cc.xy(2, 7, CellConstraints.CENTER, CellConstraints.DEFAULT));
                panel1.add(omsPass, cc.xy(3, 7));

                //---- label10 ----
                label10.setText("app-user");
                panel1.add(label10, cc.xy(2, 9, CellConstraints.CENTER, CellConstraints.DEFAULT));
                panel1.add(appUser, cc.xy(3, 9));

                //---- label11 ----
                label11.setText("app-pass");
                panel1.add(label11, cc.xy(2, 11, CellConstraints.CENTER, CellConstraints.DEFAULT));
                panel1.add(appPass, cc.xy(3, 11));
            }
            layeredPane1.add(panel1, JLayeredPane.DEFAULT_LAYER);
            panel1.setBounds(60, 90, 525, 280);

            //---- button1 ----
            button1.setText("\u6e05\u7a7atoken");
            button1.addActionListener(e -> tokenClearEvent(e));
            layeredPane1.add(button1, JLayeredPane.DEFAULT_LAYER);
            button1.setBounds(280, 380, button1.getPreferredSize().width, 40);
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JLayeredPane layeredPane1;
    private JComboBox evm;
    private JLabel label4;
    private JPanel panel1;
    private JLabel label6;
    private JTextField domain;
    private JLabel label7;
    private JTextField secret;
    private JLabel label8;
    private JTextField omsUser;
    private JLabel label9;
    private JTextField omsPass;
    private JLabel label10;
    private JTextField appUser;
    private JLabel label11;
    private JTextField appPass;
    private JButton button1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables


    public JLayeredPane getLayeredPane1() {
        return layeredPane1;
    }

    private void addJComboBox() {

        this.evm.addItem("local");
        this.evm.addItem("dev");
        this.evm.addItem("test");
        this.evm.addItem("prod");
    }


    public JComboBox getEvm() {
        return evm;
    }

    public JTextField getDomain() {
        return domain;
    }

    public JTextField getSecret() {
        return secret;
    }

    public JTextField getOmsUser() {
        return omsUser;
    }

    public JTextField getOmsPass() {
        return omsPass;
    }

    public JTextField getAppUser() {
        return appUser;
    }

    public JTextField getAppPass() {
        return appPass;
    }
}
