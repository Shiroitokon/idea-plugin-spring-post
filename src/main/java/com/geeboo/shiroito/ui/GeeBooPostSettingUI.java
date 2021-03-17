/*
 * Created by JFormDesigner on Mon Nov 09 16:19:00 CST 2020
 */

package com.geeboo.shiroito.ui;

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
        final String authorization = stringStringMap.getOrDefault(StorageKey.AUTHORIZATION, "");
        final String prefixModule = state.getPrefixModule();

        this.domain.setText(domain);
        this.secret.setText(secret);
        this.authorization.setText(authorization);
        this.prefixModule.setText(prefixModule);

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
        authorization = new JTextField();
        label9 = new JLabel();
        prefixModule = new JTextField();
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
                        new ColumnSpec(Sizes.dluX(58)),
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
                label8.setText("authorization");
                panel1.add(label8, cc.xy(2, 5, CellConstraints.CENTER, CellConstraints.DEFAULT));
                panel1.add(authorization, cc.xy(3, 5));

                //---- label9 ----
                label9.setText("prefix_module");
                panel1.add(label9, cc.xy(2, 17, CellConstraints.CENTER, CellConstraints.DEFAULT));
                panel1.add(prefixModule, cc.xy(3, 17));
            }
            layeredPane1.add(panel1, JLayeredPane.DEFAULT_LAYER);
            panel1.setBounds(60, 90, 560, 275);
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
    private JTextField authorization;
    private JLabel label9;
    private JTextField prefixModule;
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

    public JTextField getAuthorization() { return authorization;
    }

    public JTextField getPrefixModule(){return prefixModule;}

}
