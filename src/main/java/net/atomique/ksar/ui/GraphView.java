/*
 * Copyright 2018 The kSAR Project. All rights reserved.
 * See the LICENSE file in the project root for more information.
 */

package net.atomique.ksar.ui;

import net.atomique.ksar.Config;
import net.atomique.ksar.GlobalOptions;
import net.atomique.ksar.graph.Graph;
import org.jfree.chart.ChartPanel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class GraphView extends javax.swing.JPanel {

  private static final Logger log = LoggerFactory.getLogger(GraphView.class);

  /**
   * Creates new form GraphView
   */
  public GraphView() {
    initComponents();
  }


  public void setGraph(Graph graph) {
    this.thegraph = graph;
    if (mychartpanel != null) {
      graphPanel.remove(mychartpanel);
    }
    mychartpanel = graph.getChartPanel();
    graphPanel.add(mychartpanel);
    this.validate();
  }

  /**
   * This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    graphPanel = new javax.swing.JPanel();
    buttonPanel = new javax.swing.JPanel();
    csvButton = new javax.swing.JButton();
    jpgBuitton = new javax.swing.JButton();
    pngButton = new javax.swing.JButton();
    ctrlcButton = new javax.swing.JButton();
    printButton = new javax.swing.JButton();

    setLayout(new java.awt.BorderLayout());

    graphPanel.setLayout(new java.awt.BorderLayout());
    add(graphPanel, java.awt.BorderLayout.CENTER);

    csvButton.setText("Export CSV");
    csvButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        csvButtonActionPerformed(evt);
      }
    });
    buttonPanel.add(csvButton);

    jpgBuitton.setText("Export JPG");
    jpgBuitton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jpgBuittonActionPerformed(evt);
      }
    });
    buttonPanel.add(jpgBuitton);

    pngButton.setText("Export PNG");
    pngButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        pngButtonActionPerformed(evt);
      }
    });
    buttonPanel.add(pngButton);

    ctrlcButton.setText("Copy");
    ctrlcButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        ctrlcButtonActionPerformed(evt);
      }
    });
    buttonPanel.add(ctrlcButton);

    printButton.setText("Print");
    printButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        printButtonActionPerformed(evt);
      }
    });
    buttonPanel.add(printButton);

    add(buttonPanel, java.awt.BorderLayout.SOUTH);
  } // </editor-fold>//GEN-END:initComponents

  private void csvButtonActionPerformed(
      java.awt.event.ActionEvent evt) { //GEN-FIRST:event_csvButtonActionPerformed
    String filename = null;
    String buffer = null;

    filename = askSaveFilename("Export CSV", Config.getLastExportDirectory());

    if (filename == null) {
      return;
    }
    Config.setLastExportDirectory(filename);
    if (!Config.getLastExportDirectory().isDirectory()) {
      Config.setLastExportDirectory(Config.getLastExportDirectory().getParentFile());
      Config.save();
    }

    buffer = thegraph.make_csv();

    log.debug(buffer);

    BufferedWriter out = null;
    try {
      out = new BufferedWriter(new FileWriter(filename));
    } catch (IOException e) {
      out = null;
    }
    if (out == null) {
      return;
    }
    try {
      out.write(buffer);
      out.flush();
      out.close();
    } catch (IOException e) {

    }


  } //GEN-LAST:event_csvButtonActionPerformed

  private void jpgBuittonActionPerformed(
      java.awt.event.ActionEvent evt) { //GEN-FIRST:event_jpgBuittonActionPerformed
    String filename = askSaveFilename("Export JPG", Config.getLastExportDirectory());
    if (filename == null) {
      return;
    }
    thegraph.saveJPG(filename, Config.getImageWidth(), Config.getImageHeight());
  } //GEN-LAST:event_jpgBuittonActionPerformed

  private void ctrlcButtonActionPerformed(
      java.awt.event.ActionEvent evt) { //GEN-FIRST:event_ctrlcButtonActionPerformed
    mychartpanel.doCopy();
  } //GEN-LAST:event_ctrlcButtonActionPerformed

  private void printButtonActionPerformed(
      java.awt.event.ActionEvent evt) { //GEN-FIRST:event_printButtonActionPerformed
    mychartpanel.createChartPrintJob();
  } //GEN-LAST:event_printButtonActionPerformed

  private void pngButtonActionPerformed(
      java.awt.event.ActionEvent evt) { //GEN-FIRST:event_pngButtonActionPerformed
    String filename = askSaveFilename("Export PNG", Config.getLastExportDirectory());
    if (filename == null) {
      return;
    }
    thegraph.savePNG(filename, Config.getImageWidth(), Config.getImageHeight());
  } //GEN-LAST:event_pngButtonActionPerformed


  private String askSaveFilename(String title, File chdirto) {
    String filename = null;
    JFileChooser chooser = new JFileChooser();
    chooser.setDialogTitle(title);
    if (chdirto != null) {
      chooser.setCurrentDirectory(chdirto);
    }
    int returnVal = chooser.showSaveDialog(GlobalOptions.getUI());
    if (returnVal == JFileChooser.APPROVE_OPTION) {
      filename = chooser.getSelectedFile().getAbsolutePath();
    }

    if (filename == null) {
      return null;
    }

    if (new File(filename).exists()) {
      String[] choix = {"Yes", "No"};
      int resultat =
          JOptionPane.showOptionDialog(null, "Overwrite " + filename + " ?", "File Exist",
              JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, choix,
              choix[1]);
      if (resultat != 0) {
        return null;
      }
    }
    return filename;
  }

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JPanel buttonPanel;
  private javax.swing.JButton csvButton;
  private javax.swing.JButton ctrlcButton;
  private javax.swing.JPanel graphPanel;
  private javax.swing.JButton jpgBuitton;
  private javax.swing.JButton pngButton;
  private javax.swing.JButton printButton;
  // End of variables declaration//GEN-END:variables
  private Graph thegraph = null;
  private ChartPanel mychartpanel = null;
}
