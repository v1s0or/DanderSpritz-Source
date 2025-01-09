package ddb.start;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.TreeSet;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import org.jdesktop.layout.GroupLayout;
import sun.misc.URLClassPath;

public class Start extends JFrame {
   public static final String PATH_TOOLCHAIN = "java-j2se_1.6-sun";
   public static final String PATH_LIBRARY = "lib";
   public static final String OPERATION_DISK = "OpsDisk";
   public static final String RESOURCE_DIR = "ResourceDir";
   public static final String LOG_DIR = "LogDir";
   public static final String CONFIG_DIR = "ConfigDir";
   public static final String LOCAL_ADDRESS = "LocalAddress";
   public static final String BUILD_TYPE = "BuildType";
   public static final String GUI_TYPE = "GuiType";
   public static final String OPERATION_MODE = "OpMode";
   public static final String LOAD_PREVIOUS = "LoadPrevious";
   public static final String LOCAL_MODE = "LocalMode";
   public static final String JAVA_EXE = "java.exe";
   public static final String VMARGS = "vmargs";
   public static final String RES_DIR = "res.dir";
   public static final String DEBUGVMARGS = "vmargs.debug";
   public static final String CLASSPATH = "classpath.dirs";
   public static final String JAR_DIRS = "jar.dirs";
   public static final String LIVE_OPERATION = "live.operation";
   public static final String REPLAY_OPERATION = "replay.operation";
   public static final String WINDOWS_START = "windows.start";
   public static final String BUILD_RELEASE = "build.release";
   public static final String BUILD_DEBUG = "build.debug";
   public static final String BUILD_DEBUG_WINDOWS = "build.debug.windows";
   public static final String SHOW_OP_TYPE = "show.optype";
   public static final String SHOW_DEBUG_CORE = "show.debug.core";
   public static final String SHOW_DEBUG_GUI = "show.debug.gui";
   public static final String SHOW_LOCAL_MODE = "show.local.mode";
   public static final String SHOW_THREAD_DUMP = "show.thread.dump";
   public static final String WINDOWS = "windows";
   public static final String LINUX = "linux";
   public static final String PATH_VAR = "path.var";
   public static final String TOOL_CHAIN_STR = "tool.chain";
   public static final String PATH_SEP = "path.sep";
   public static final String THREAD_DUMP = "thread.dump";
   public static final String WAIT_FOR_OUTPUT = "wait.for.output";
   public static final String DSZ_KEYWORD = "DSZ_KEYWORD";
   public static final String LIVE_KEYWORD = String.format("live.%s", "DSZ_KEYWORD");
   public static final String REPLAY_KEYWORD = String.format("replay.%s", "DSZ_KEYWORD");
   public static final String DSZ_DEFAULT = "Default";
   static Properties prop = new Properties();
   static Properties userDefaults = new Properties();
   public static final String START_PROPERTIES = "start.properties";
   public static final String USER_DEFAULTS = "user.defaults";
   boolean guess = false;
   JFileChooser directoryFinder = null;
   private static char[] INVALIDCHARACTERS = new char[]{'\t', ' ', '\b', '\n', '\r'};
   File themeSearchRoot = null;
   DefaultComboBoxModel liveOperationThemes = new DefaultComboBoxModel();
   DefaultComboBoxModel replayOperationThemes = new DefaultComboBoxModel();
   JRadioButton buildDebug;
   JRadioButton buildRelease;
   JButton configurationBrowse;
   JTextField configurationField;
   JLabel configurationLabel;
   ButtonGroup coreBuild;
   JPanel corePanel;
   JButton goButton;
   ButtonGroup guiBuild;
   JRadioButton guiDebug;
   JPanel guiPanel;
   JRadioButton guiRelease;
   JPanel jPanel1;
   JPanel jPanel2;
   JRadioButton liveOption;
   JCheckBox loadPrevious;
   JTextField localCommsAddressField;
   JLabel localCommsAddressLabel;
   JCheckBox localMode;
   JButton logBrowse;
   JTextField logField;
   JLabel logLabel;
   JButton operationBrowse;
   JTextField operationField;
   JLabel operationLabel;
   JPanel operationPanel;
   ButtonGroup operationType;
   JPanel optionsPanel;
   JRadioButton replayOption;
   JButton resourceBrowse;
   JTextField resourceField;
   JLabel resourceLabel;
   JComboBox themeSelector;
   JCheckBox threadDump;
   JCheckBox waitFor;
   static final Pattern[] patterns = new Pattern[]{Pattern.compile("[0-9a-fA-F]{1,8}"), Pattern.compile("[Zz][0-2]{0,1}[0-9]{0,2}\\.[0-2]{0,1}[0-9]{0,2}\\.[0-2]{0,1}[0-9]{0,2}\\.[0-2]{0,1}[0-9]{0,2}")};
   private static FilenameFilter jars = new FilenameFilter() {
      public boolean accept(File var1, String var2) {
         return var2.toLowerCase().endsWith(".jar");
      }
   };

   public Start() {
      try {
         this.directoryFinder = new JFileChooser();
         this.directoryFinder.setFileSelectionMode(1);
      } catch (Exception var4) {
         var4.printStackTrace();
      }

      try {
         prop.load(new FileInputStream("start.properties"));
      } catch (FileNotFoundException var2) {
         var2.printStackTrace();
      } catch (IOException var3) {
         var3.printStackTrace();
      }

      this.initComponents();

      try {
         userDefaults.load(new FileInputStream("user.defaults"));
         this.operationField.setText(getStringDefault("OpsDisk", ""));
         this.resourceField.setText(getStringDefault("ResourceDir", ""));
         this.logField.setText(getStringDefault("LogDir", ""));
         this.configurationField.setText(getStringDefault("ConfigDir", ""));
         File var1 = new File(".");
         if (this.operationField.getText() != null && this.operationField.getText().length() != 0) {
            var1 = new File(this.operationField.getText());
         } else {
            this.operationField.setText(var1.getCanonicalPath());
         }

         this.infer(var1.getCanonicalFile());
      } catch (FileNotFoundException var5) {
         this.guess = true;
      } catch (Exception var6) {
         var6.printStackTrace();
         this.guess = true;
      }

      if (this.guess) {
         this.infer(new File("."));
         this.examine();
      }

   }

   private void initComponents() {
      this.guiBuild = new ButtonGroup();
      this.operationType = new ButtonGroup();
      this.coreBuild = new ButtonGroup();
      this.resourceField = new JTextField();
      this.logField = new JTextField();
      this.configurationField = new JTextField();
      this.operationField = new JTextField();
      this.resourceLabel = new JLabel();
      this.logLabel = new JLabel();
      this.configurationLabel = new JLabel();
      this.operationLabel = new JLabel();
      this.resourceBrowse = new JButton();
      this.logBrowse = new JButton();
      this.configurationBrowse = new JButton();
      this.operationBrowse = new JButton();
      this.goButton = new JButton();
      this.localCommsAddressLabel = new JLabel();
      this.localCommsAddressField = new JTextField();
      this.jPanel1 = new JPanel();
      this.operationPanel = new JPanel();
      this.liveOption = new JRadioButton();
      this.replayOption = new JRadioButton();
      this.optionsPanel = new JPanel();
      this.loadPrevious = new JCheckBox();
      this.localMode = new JCheckBox();
      this.corePanel = new JPanel();
      this.buildDebug = new JRadioButton();
      this.buildRelease = new JRadioButton();
      this.guiPanel = new JPanel();
      this.guiRelease = new JRadioButton();
      this.guiDebug = new JRadioButton();
      this.waitFor = new JCheckBox();
      this.threadDump = new JCheckBox();
      this.jPanel2 = new JPanel();
      this.themeSelector = new JComboBox();
      this.setDefaultCloseOperation(2);
      this.setTitle("DanderSpritz Operation Center");
      this.setCursor(new Cursor(0));
      this.setLocationByPlatform(true);
      this.setName("startFrame");
      this.setResizable(false);
      this.resourceField.setToolTipText(prop.getProperty("tooltip.resource"));
      this.resourceField.addKeyListener(new KeyAdapter() {
         public void keyReleased(KeyEvent var1) {
            Start.this.enterPressed(var1);
         }
      });
      this.logField.setToolTipText(prop.getProperty("tooltip.log"));
      this.logField.addKeyListener(new KeyAdapter() {
         public void keyReleased(KeyEvent var1) {
            Start.this.enterPressed(var1);
         }
      });
      this.configurationField.setToolTipText(prop.getProperty("tooltip.config"));
      this.configurationField.addKeyListener(new KeyAdapter() {
         public void keyReleased(KeyEvent var1) {
            Start.this.enterPressed(var1);
         }
      });
      this.operationField.setToolTipText(prop.getProperty("tooltip.disk"));
      this.operationField.addKeyListener(new KeyAdapter() {
         public void keyReleased(KeyEvent var1) {
            Start.this.enterPressed(var1);
         }
      });
      this.resourceLabel.setText(prop.getProperty("label.resource"));
      this.logLabel.setText(prop.getProperty("label.log"));
      this.configurationLabel.setText(prop.getProperty("label.config"));
      this.operationLabel.setText(prop.getProperty("label.disk"));
      this.resourceBrowse.setText(prop.getProperty("label.browse"));
      this.resourceBrowse.setToolTipText(prop.getProperty("tooltip.resource.browse"));
      this.resourceBrowse.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            Start.this.resourceBrowseActionPerformed(var1);
         }
      });
      this.logBrowse.setText(prop.getProperty("label.browse"));
      this.logBrowse.setToolTipText(prop.getProperty("tooltip.log.browse"));
      this.logBrowse.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            Start.this.logBrowseActionPerformed(var1);
         }
      });
      this.configurationBrowse.setText(prop.getProperty("label.browse"));
      this.configurationBrowse.setToolTipText(prop.getProperty("tooltip.config.browse"));
      this.configurationBrowse.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            Start.this.configurationBrowseActionPerformed(var1);
         }
      });
      this.operationBrowse.setText(prop.getProperty("label.browse"));
      this.operationBrowse.setToolTipText(prop.getProperty("tooltip.disk.browse"));
      this.operationBrowse.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            Start.this.operationBrowseActionPerformed(var1);
         }
      });
      this.goButton.setText(prop.getProperty("label.start"));
      this.goButton.setToolTipText(prop.getProperty("tooltip.start"));
      this.goButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            Start.this.goButtonActionPerformed(var1);
         }
      });
      this.localCommsAddressLabel.setText(prop.getProperty("label.comms"));
      this.localCommsAddressField.setText("z0.0.0.1");
      this.localCommsAddressField.addKeyListener(new KeyAdapter() {
         public void keyReleased(KeyEvent var1) {
            Start.this.enterPressed(var1);
         }
      });
      this.operationPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), prop.getProperty("label.opMode")));
      this.operationType.add(this.liveOption);
      this.liveOption.setSelected(true);
      this.liveOption.setText(prop.getProperty("label.live"));
      this.liveOption.setToolTipText(prop.getProperty("tooltip.live"));
      this.liveOption.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
      this.liveOption.setMargin(new Insets(0, 0, 0, 0));
      this.liveOption.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            Start.this.liveOptionActionPerformed(var1);
         }
      });
      this.operationType.add(this.replayOption);
      this.replayOption.setText(prop.getProperty("label.replay"));
      this.replayOption.setToolTipText(prop.getProperty("tooltip.replay"));
      this.replayOption.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
      this.replayOption.setMargin(new Insets(0, 0, 0, 0));
      this.replayOption.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent var1) {
            Start.this.replayOptionActionPerformed(var1);
         }
      });
      GroupLayout var1 = new GroupLayout(this.operationPanel);
      this.operationPanel.setLayout(var1);
      var1.setHorizontalGroup(var1.createParallelGroup(1).add(var1.createSequentialGroup().add(var1.createParallelGroup(1).add(this.liveOption).add(this.replayOption)).addContainerGap(45, 32767)));
      var1.setVerticalGroup(var1.createParallelGroup(1).add(var1.createSequentialGroup().add(this.liveOption).addPreferredGap(0).add(this.replayOption).addContainerGap(-1, 32767)));
      this.optionsPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), prop.getProperty("label.options")));
      this.loadPrevious.setText(prop.getProperty("label.loadPrevious"));
      this.loadPrevious.setToolTipText(prop.getProperty("tooltip.loadPrevious"));
      this.loadPrevious.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
      this.loadPrevious.setMargin(new Insets(0, 0, 0, 0));
      this.localMode.setText(prop.getProperty("label.localMode"));
      this.localMode.setToolTipText(prop.getProperty("tooltip.localMode"));
      this.localMode.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
      this.localMode.setMargin(new Insets(0, 0, 0, 0));
      this.localMode.setVisible(this.isShowLocal());
      GroupLayout var2 = new GroupLayout(this.optionsPanel);
      this.optionsPanel.setLayout(var2);
      var2.setHorizontalGroup(var2.createParallelGroup(1).add(var2.createSequentialGroup().addContainerGap().add(var2.createParallelGroup(1).add(this.loadPrevious).add(this.localMode)).addContainerGap(-1, 32767)));
      var2.setVerticalGroup(var2.createParallelGroup(1).add(var2.createSequentialGroup().add(this.loadPrevious).addPreferredGap(0).add(this.localMode).addContainerGap(-1, 32767)));
      this.corePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), prop.getProperty("label.core")));
      this.corePanel.setVisible(this.isShowDebugCore());
      this.coreBuild.add(this.buildDebug);
      this.buildDebug.setText(prop.getProperty("label.debug"));
      this.buildDebug.setToolTipText(prop.getProperty("tooltip.debug"));
      this.buildDebug.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
      this.buildDebug.setMargin(new Insets(0, 0, 0, 0));
      this.coreBuild.add(this.buildRelease);
      this.buildRelease.setSelected(true);
      this.buildRelease.setText(prop.getProperty("label.release"));
      this.buildRelease.setToolTipText(prop.getProperty("tooltip.release"));
      this.buildRelease.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
      this.buildRelease.setMargin(new Insets(0, 0, 0, 0));
      GroupLayout var3 = new GroupLayout(this.corePanel);
      this.corePanel.setLayout(var3);
      var3.setHorizontalGroup(var3.createParallelGroup(1).add(var3.createSequentialGroup().addContainerGap().add(var3.createParallelGroup(1).add(this.buildRelease).add(this.buildDebug)).addContainerGap(-1, 32767)));
      var3.setVerticalGroup(var3.createParallelGroup(1).add(var3.createSequentialGroup().add(this.buildRelease).addPreferredGap(0).add(this.buildDebug).addContainerGap(-1, 32767)));
      this.guiPanel.setBorder(BorderFactory.createTitledBorder(prop.getProperty("label.gui")));
      this.guiPanel.setVisible(this.isShowDebugGui());
      this.guiBuild.add(this.guiRelease);
      this.guiRelease.setText(prop.getProperty("label.release"));
      this.guiRelease.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
      this.guiRelease.setMargin(new Insets(0, 0, 0, 0));
      this.guiBuild.add(this.guiDebug);
      this.guiDebug.setText(prop.getProperty("label.debug"));
      this.guiDebug.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
      this.guiDebug.setMargin(new Insets(0, 0, 0, 0));
      GroupLayout var4 = new GroupLayout(this.guiPanel);
      this.guiPanel.setLayout(var4);
      var4.setHorizontalGroup(var4.createParallelGroup(1).add(var4.createSequentialGroup().addContainerGap().add(var4.createParallelGroup(1).add(this.guiRelease).add(this.guiDebug)).addContainerGap(-1, 32767)));
      var4.setVerticalGroup(var4.createParallelGroup(1).add(var4.createSequentialGroup().add(this.guiRelease).addPreferredGap(0).add(this.guiDebug).addContainerGap(-1, 32767)));
      this.waitFor.setText("Wait For Output");
      this.waitFor.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
      this.waitFor.setMargin(new Insets(0, 0, 0, 0));
      this.waitFor.setVisible(false);
      this.threadDump.setText("Thread Dump");
      this.threadDump.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
      this.threadDump.setMargin(new Insets(0, 0, 0, 0));
      this.threadDump.setVisible(this.isShowThreadDump());
      this.jPanel2.setBorder(BorderFactory.createTitledBorder("Theme"));
      this.themeSelector.setModel(new DefaultComboBoxModel(new String[]{"Item 1", "Item 2", "Item 3", "Item 4"}));
      GroupLayout var5 = new GroupLayout(this.jPanel2);
      this.jPanel2.setLayout(var5);
      var5.setHorizontalGroup(var5.createParallelGroup(1).add(var5.createSequentialGroup().addContainerGap().add(this.themeSelector, 0, 179, 32767).addContainerGap()));
      var5.setVerticalGroup(var5.createParallelGroup(1).add(var5.createSequentialGroup().add(this.themeSelector, -2, -1, -2).addContainerGap(25, 32767)));
      GroupLayout var6 = new GroupLayout(this.jPanel1);
      this.jPanel1.setLayout(var6);
      var6.setHorizontalGroup(var6.createParallelGroup(1).add(var6.createSequentialGroup().addContainerGap().add(this.operationPanel, -2, -1, -2).addPreferredGap(0).add(this.optionsPanel, -2, -1, -2).addPreferredGap(0).add(this.corePanel, -2, -1, -2).addPreferredGap(0).add(this.guiPanel, -2, -1, -2).addPreferredGap(0).add(this.jPanel2, -1, -1, 32767).addPreferredGap(0).add(var6.createParallelGroup(1, false).add(this.threadDump, -1, -1, 32767).add(this.waitFor)).addContainerGap()));
      var6.setVerticalGroup(var6.createParallelGroup(1).add(var6.createSequentialGroup().addContainerGap().add(var6.createParallelGroup(1).add(var6.createSequentialGroup().add(this.threadDump).addPreferredGap(0).add(this.waitFor)).add(2, this.jPanel2, -1, -1, 32767).add(this.operationPanel, -1, -1, 32767).add(this.optionsPanel, -1, -1, 32767).add(this.corePanel, -1, -1, 32767).add(this.guiPanel, -1, -1, 32767)).addContainerGap()));
      GroupLayout var7 = new GroupLayout(this.getContentPane());
      this.getContentPane().setLayout(var7);
      var7.setHorizontalGroup(var7.createParallelGroup(1).add(var7.createSequentialGroup().addContainerGap().add(var7.createParallelGroup(1).add(var7.createSequentialGroup().add(var7.createParallelGroup(1).add(var7.createSequentialGroup().add(var7.createParallelGroup(1).add(this.resourceLabel).add(this.logLabel).add(this.configurationLabel)).addPreferredGap(0).add(var7.createParallelGroup(1).add(this.configurationField, -1, 771, 32767).add(this.operationField, -1, 771, 32767).add(this.logField, -1, 771, 32767).add(2, this.resourceField, -1, 771, 32767).add(this.localCommsAddressField, -1, 771, 32767)).add(6, 6, 6)).add(var7.createSequentialGroup().add(this.operationLabel).addPreferredGap(0, 775, 32767))).addPreferredGap(0).add(var7.createParallelGroup(2).add(var7.createParallelGroup(1).add(this.resourceBrowse).add(this.logBrowse).add(this.configurationBrowse)).add(this.operationBrowse))).add(this.localCommsAddressLabel).add(var7.createSequentialGroup().add(this.jPanel1, -2, -1, -2).addPreferredGap(0, -1, 32767).add(this.goButton))).addContainerGap()));
      var7.setVerticalGroup(var7.createParallelGroup(1).add(var7.createSequentialGroup().addContainerGap().add(var7.createParallelGroup(3).add(this.operationLabel).add(this.operationBrowse).add(this.operationField, -2, -1, -2)).addPreferredGap(0).add(var7.createParallelGroup(1).add(var7.createParallelGroup(3).add(this.resourceLabel).add(this.resourceBrowse)).add(var7.createSequentialGroup().add(3, 3, 3).add(this.resourceField, -2, -1, -2))).addPreferredGap(0).add(var7.createParallelGroup(3).add(this.logLabel).add(this.logBrowse).add(this.logField, -2, -1, -2)).addPreferredGap(0).add(var7.createParallelGroup(3).add(this.configurationLabel).add(this.configurationBrowse).add(this.configurationField, -2, -1, -2)).addPreferredGap(0).add(var7.createParallelGroup(2).add(var7.createSequentialGroup().add(var7.createParallelGroup(3).add(this.localCommsAddressLabel).add(this.localCommsAddressField, -2, -1, -2)).addPreferredGap(0).add(this.jPanel1, -2, -1, -2)).add(this.goButton)).addContainerGap(-1, 32767)));
      this.pack();
   }

   private void replayOptionActionPerformed(ActionEvent var1) {
      if (this.replayOption.isSelected()) {
         this.loadPrevious.setSelected(true);
      }

      if (this.replayOption.isSelected()) {
         this.themeSelector.setModel(this.replayOperationThemes);
      } else {
         this.themeSelector.setModel(this.liveOperationThemes);
      }

   }

   private void liveOptionActionPerformed(ActionEvent var1) {
      if (this.liveOption.isSelected()) {
         this.loadPrevious.setSelected(false);
      }

      if (this.replayOption.isSelected()) {
         this.themeSelector.setModel(this.replayOperationThemes);
      } else {
         this.themeSelector.setModel(this.liveOperationThemes);
      }

   }

   private void operationBrowseActionPerformed(ActionEvent var1) {
      if (this.setDirectory(this.operationField, "Select the operations disk")) {
         this.infer(new File(this.operationField.getText()));
      }

   }

   private void goButtonActionPerformed(ActionEvent var1) {
      this.DanderSpritzBegin();
   }

   private void configurationBrowseActionPerformed(ActionEvent var1) {
      if (this.setDirectory(this.configurationField, "Select the configuration directory")) {
         this.infer(new File(this.configurationField.getText() + "/../"));
      }

   }

   private void logBrowseActionPerformed(ActionEvent var1) {
      if (this.setDirectory(this.logField, "Select the log directory")) {
         this.infer(new File(this.logField.getText() + "/../"));
      }

   }

   private void resourceBrowseActionPerformed(ActionEvent var1) {
      if (this.setDirectory(this.resourceField, "Select the resource directory")) {
         this.infer(new File(this.resourceField.getText() + "/../"));
      }

   }

   private void enterPressed(KeyEvent var1) {
      if (var1.getKeyCode() == 10) {
         this.DanderSpritzBegin();
      } else {
         this.examine();
      }

   }

   boolean setDirectory(JTextField var1, String var2) {
      if (this.directoryFinder == null) {
         JOptionPane.showMessageDialog(this, "The File Selector dialog did not initialize.  You must enter your paths manually.", "File Selector not available", 2);
         return false;
      } else {
         this.directoryFinder.setDialogTitle(var2);
         if (var1.getText().trim().length() > 0) {
            File var3 = new File(var1.getText().trim());
            this.directoryFinder.setSelectedFile(var3);
            this.directoryFinder.setCurrentDirectory(var3.getParentFile());
         }

         if (this.directoryFinder.showDialog(this, "Select") == 0) {
            var1.setText(this.directoryFinder.getSelectedFile().getAbsolutePath());
            return true;
         } else {
            return false;
         }
      }
   }

   void infer(File var1) {
      if (var1 != null) {
         try {
            if (this.operationField.getText().trim().length() == 0) {
               this.operationField.setText((new File(var1.getAbsolutePath())).getCanonicalPath());
            }

            if (this.resourceField.getText().trim().length() == 0) {
               File var2 = new File(var1.getAbsolutePath(), "/Resources/");
               this.resourceField.setText(var2.getCanonicalPath());
               this.searchOutThemes(var2);
            }

            if (this.logField.getText().trim().length() == 0) {
               this.logField.setText((new File(var1.getAbsolutePath(), "/Logs/")).getCanonicalPath());
            }

            if (this.configurationField.getText().trim().length() == 0) {
               this.configurationField.setText((new File(var1.getAbsolutePath(), "/UserConfiguration/")).getCanonicalPath());
            }
         } catch (IOException var3) {
            var3.printStackTrace();
         }

         this.examine();
      }
   }

   void searchOutThemes(File var1) {
      if (var1 != this.themeSearchRoot) {
         if (var1 == null) {
            this.themeSelector.setEnabled(false);
            this.themeSelector.setSelectedItem((Object)null);
         } else if (!var1.equals(this.themeSearchRoot)) {
            this.themeSearchRoot = var1;
            this.liveOperationThemes.removeAllElements();
            this.replayOperationThemes.removeAllElements();
            String var2 = "Gui/Config/";
            Vector var3 = new Vector();
            var3.add(".");
            File[] var4 = var1.listFiles();
            if (var4 != null) {
               File[] var5 = var4;
               int var6 = var4.length;

               for(int var7 = 0; var7 < var6; ++var7) {
                  File var8 = var5[var7];
                  if (var8.isDirectory()) {
                     var3.add(var8.getName());
                  }
               }
            }

            Pattern var17 = Pattern.compile("systemStartup_([^.]+).xml");
            Pattern var18 = Pattern.compile("replay_([^.]+).xml");
            TreeSet var19 = new TreeSet();
            TreeSet var20 = new TreeSet();
            Iterator var9 = var3.iterator();

            while(true) {
               do {
                  String var10;
                  if (!var9.hasNext()) {
                     this.liveOperationThemes.addElement("Default");
                     var9 = var19.iterator();

                     while(var9.hasNext()) {
                        var10 = (String)var9.next();
                        this.liveOperationThemes.addElement(var10);
                     }

                     this.replayOperationThemes.addElement("Default");
                     var9 = var20.iterator();

                     while(var9.hasNext()) {
                        var10 = (String)var9.next();
                        this.replayOperationThemes.addElement(var10);
                     }

                     return;
                  }

                  var10 = (String)var9.next();
                  File var11 = new File(this.themeSearchRoot, String.format("%s/%s", var10, var2));
                  var4 = var11.listFiles();
               } while(var4 == null);

               File[] var12 = var4;
               int var13 = var4.length;

               for(int var14 = 0; var14 < var13; ++var14) {
                  File var15 = var12[var14];
                  if (var15.isFile()) {
                     Matcher var16 = var17.matcher(var15.getName());
                     if (var16.matches()) {
                        var19.add(var16.group(1));
                     }

                     var16 = var18.matcher(var15.getName());
                     if (var16.matches()) {
                        var20.add(var16.group(1));
                     }
                  }
               }
            }
         }
      }
   }

   public static void main(String[] var0) {
      String var1 = "com.birosoft.liquid.LiquidLookAndFeel";
      System.setProperty("swing.defaultlaf", var1);

      try {
         UIManager.setLookAndFeel(var1);
      } catch (Exception var10) {
         var10.printStackTrace();
      }

      try {
         Start var2 = new Start();
         String var3 = getStringDefault(LIVE_KEYWORD);
         var2.liveOperationThemes.setSelectedItem("Default");
         int var4;
         if (var3 != null) {
            for(var4 = 0; var4 < var2.liveOperationThemes.getSize(); ++var4) {
               if (var3.equals(var2.liveOperationThemes.getElementAt(var4))) {
                  var2.liveOperationThemes.setSelectedItem(var3);
                  break;
               }
            }
         }

         var3 = getStringDefault(REPLAY_KEYWORD);
         var2.replayOperationThemes.setSelectedItem("Default");
         if (var3 != null) {
            for(var4 = 0; var4 < var2.replayOperationThemes.getSize(); ++var4) {
               if (var3.equals(var2.replayOperationThemes.getElementAt(var4))) {
                  var2.replayOperationThemes.setSelectedItem(var3);
                  break;
               }
            }
         }

         if (getBooleanDefault("OpMode", true)) {
            var2.liveOption.setSelected(true);
            var2.themeSelector.setModel(var2.liveOperationThemes);
         } else {
            var2.replayOption.setSelected(true);
            var2.themeSelector.setModel(var2.replayOperationThemes);
         }

         String var12 = var2.resourceField.getText();
         if (var12.endsWith(prop.getProperty("res.dir", "Dsz"))) {
            var12 = var12.substring(0, var12.lastIndexOf(prop.getProperty("res.dir", "Dsz")));
            var2.resourceField.setText(var12);
         }

         var2.operationPanel.setVisible(var2.isShowOpType());
         if (var2.isShowDebugCore()) {
            var2.buildRelease.setSelected(getBooleanDefault("BuildType", true));
            var2.buildDebug.setSelected(!getBooleanDefault("BuildType", true));
         } else {
            var2.buildRelease.setSelected(true);
            var2.buildDebug.setSelected(false);
         }

         if (var2.isShowDebugGui()) {
            var2.guiRelease.setSelected(getBooleanDefault("GuiType", true));
            var2.guiDebug.setSelected(!getBooleanDefault("GuiType", true));
         } else {
            var2.guiRelease.setSelected(true);
            var2.guiDebug.setSelected(false);
         }

         if (var2.isShowLocal()) {
            var2.localMode.setSelected(getBooleanDefault("LocalMode", false));
         } else {
            var2.localMode.setSelected(false);
         }

         var2.loadPrevious.setSelected(getBooleanDefault("LoadPrevious", false));
         if (var2.isShowThreadDump()) {
            var2.threadDump.setSelected(getBooleanDefault("thread.dump", false));
         }

         var2.waitFor.setSelected(getBooleanDefault("wait.for.output", false));
         boolean var5 = false;

         for(int var6 = 0; var6 < var0.length; ++var6) {
            String var7 = var0[var6];
            String[] var8 = var7.split("=", 2);
            if (var8.length == 0) {
               doHelp(var2);
               System.exit(0);
            }

            String var9 = var8[0].toLowerCase();
            if (var9.equals("-core")) {
               if (var8.length != 2) {
                  doHelp(var2);
                  return;
               }

               var8[1] = var8[1].toLowerCase();
               if (var8[1].equals("debug")) {
                  var2.buildDebug.setSelected(true);
               } else {
                  if (!var8[1].equals("release")) {
                     doHelp(var2);
                     return;
                  }

                  var2.buildRelease.setSelected(true);
               }
            } else if (var9.equals("-gui")) {
               if (var8.length != 2) {
                  doHelp(var2);
                  return;
               }

               var8[1] = var8[1].toLowerCase();
               if (var8[1].equals("debug")) {
                  var2.guiDebug.setSelected(true);
               } else {
                  if (!var8[1].equals("release")) {
                     doHelp(var2);
                     return;
                  }

                  var2.guiRelease.setSelected(true);
               }
            } else if (var9.equals("-debug")) {
               var2.buildDebug.setSelected(true);
            } else if (var9.equals("-release")) {
               var2.buildRelease.setSelected(true);
            } else if (var9.equals("-local")) {
               var2.localMode.setSelected(true);
            } else if (var9.equals("-previous")) {
               var2.loadPrevious.setSelected(true);
            } else if (var9.equals("-live")) {
               var2.liveOption.setSelected(true);
            } else if (var9.equals("-replay")) {
               var2.replayOption.setSelected(true);
            } else if (var9.equals("-opsdisk") && var8.length == 2) {
               var2.operationField.setText(var8[1]);
            } else if (var9.equals("-resource") && var8.length == 2) {
               var2.resourceField.setText(var8[1]);
            } else if (var9.equals("-log") && var8.length == 2) {
               var2.logField.setText(var8[1]);
            } else if (var9.equals("-config") && var8.length == 2) {
               var2.configurationField.setText(var8[1]);
            } else if (var9.equals("-load")) {
               var5 = true;
            } else {
               doHelp(var2);
               System.exit(0);
            }
         }

         if (var5 && var2.isReady()) {
            var2.DanderSpritzBegin();
         } else {
            var2.setVisible(true);
         }
      } catch (Throwable var11) {
         var11.printStackTrace();
         JOptionPane.showMessageDialog((Component)null, "Start.jar requires Java SE6", "Incorrect Runtime", 0);
      }

   }

   public static String getStringDefault(String var0) {
      return getStringDefault(var0, "");
   }

   public static String getStringDefault(String var0, String var1) {
      try {
         return userDefaults.getProperty(var0, var1);
      } catch (Exception var3) {
         return var1;
      }
   }

   public static Boolean getBooleanDefault(String var0) {
      return getBooleanDefault(var0, Boolean.TRUE);
   }

   public static Boolean getBooleanDefault(String var0, Boolean var1) {
      try {
         return Boolean.parseBoolean(userDefaults.getProperty(var0, var1.toString()));
      } catch (Exception var3) {
         return var1;
      }
   }

   public static void setStringDefault(String var0, String var1) {
      if (var1 == null) {
         userDefaults.remove(var0);
      } else {
         userDefaults.setProperty(var0, var1);
      }

   }

   public static void setBooleanDefault(String var0, Boolean var1) {
      if (var1 == null) {
         setStringDefault(var0, (String)null);
      } else {
         setStringDefault(var0, var1.toString());
      }

   }

   public static void doHelp(Start var0) {
      StringWriter var1 = new StringWriter();
      PrintWriter var2 = new PrintWriter(var1);
      new StringBuilder();
      var2.println("Help for Start.jar");
      var2.println("Start.jar [-previous] [-live|-replay] [-opsdisk=DIR] [-resource=DIR] [-log=DIR] [-config=DIR] [-load]");
      var2.println("\t[-previous]:  Automatically load previous operations");
      var2.println("\t[-live]:  A live operation");
      var2.println("\t[-replay]:  A replay operation");
      var2.println("\t[-opsdisk=DIR]:  Set the operations disk value to the given DIR");
      var2.println("\t[-resource=DIR]:  Set the resource directory to the given DIR");
      var2.println("\t[-log=DIR]:  Set the log directory to the given DIR");
      var2.println("\t[-config=DIR]:  Set the user configuration directory to the given DIR");
      if (var0.isShowDebugCore() || var0.isShowLocal() || var0.isShowDebugGui()) {
         var2.println("\nExtra Parameters:");
         if (var0.isShowDebugCore()) {
            var2.println("\t[-core=<debug|release>]");
            var2.println("\t\t[debug]: Tells DanderSpritz to load the debug version of the core");
            var2.println("\t\t[release]: Tells DanderSpritz to load the release version of the core");
         }

         if (var0.isShowDebugGui()) {
            var2.println("\t[-gui=<debug|release>]");
            var2.println("\t\t[-debug]: Tells DanderSpritz to load the debug version of the gui");
            var2.println("\t\t[-release]: Tells DanderSpritz to load the release version of the gui");
         }

         if (var0.isShowLocal()) {
            var2.println("\t[-local]:  Turns on local mode");
         }
      }

      JTextArea var4 = new JTextArea();
      var4.setFont(new Font("Monospaced", 0, 14));
      var4.setTabSize(4);
      JScrollPane var5 = new JScrollPane(var4);
      Dimension var6 = new Dimension(900, 350);
      var5.setMinimumSize(var6);
      var5.setPreferredSize(var6);
      var5.setSize(var6);
      var5.setMaximumSize(var6);
      var4.setText(var1.toString());
      JOptionPane.showMessageDialog((Component)null, var5, "Start Help", 1);
   }

   private boolean evaluatePath(String var1, boolean var2) {
      char[] var3 = INVALIDCHARACTERS;
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         char var6 = var3[var5];
         if (var1.indexOf(var6) >= 0) {
            return false;
         }
      }

      File var7 = new File(var1);
      if (var7.exists() && var7.isDirectory()) {
         return true;
      } else if (!var7.exists()) {
         if (var2) {
            return false;
         } else {
            return var7.mkdirs();
         }
      } else {
         return false;
      }
   }

   public boolean evaluate() {
      if (!this.evaluatePath(this.operationField.getText(), true)) {
         return this.error("Operation Disk location '" + this.operationField.getText() + "' does not exist or is not a directory");
      } else if (!this.evaluatePath(this.resourceField.getText(), true)) {
         return this.error("Resource location '" + this.resourceField.getText() + "' does not exist or is not a directory");
      } else if (!this.evaluatePath(this.logField.getText(), false)) {
         return this.error("Log directory '" + this.logField.getText() + "' is not a directory");
      } else if (!this.evaluatePath(this.configurationField.getText(), false)) {
         return this.error("Configuration directory '" + this.configurationField.getText() + "' is not a directory");
      } else {
         return !this.isValidId(this.localCommsAddressField.getText()) ? this.error(String.format("Comms Address '%s' is invalid", this.localCommsAddressField.getText())) : true;
      }
   }

   private boolean isValidId(String var1) {
      Pattern[] var2 = patterns;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Pattern var5 = var2[var4];
         if (var5.matcher(var1).matches()) {
            return true;
         }
      }

      return false;
   }

   public boolean error(String var1) {
      JOptionPane.showMessageDialog(this, var1, "Invalid parameters", 0);
      return false;
   }

   public boolean isReady() {
      return !this.guess && this.evaluate() && this.examine();
   }

   public boolean isDir(String var1) {
      if (var1 == null) {
         return false;
      } else if (var1.length() == 0) {
         return false;
      } else {
         File var2 = new File(var1);
         return var2.exists() ? var2.isDirectory() : var2.mkdirs();
      }
   }

   public void DanderSpritzBegin() {
      if (this.evaluate()) {
         (new Thread(new Runnable() {
            public void run() {
               Start.this.beginImpl();
            }
         }, "Start DanderSpritz")).start();
      }
   }

   private void beginImpl() {
      File var1 = new File(this.configurationField.getText(), "testfile.dsz");
      File var2 = var1.getParentFile();
      boolean var3 = false;
      var2.mkdirs();
      if (var1.exists()) {
         if (var1.delete()) {
            var3 = true;
         }
      } else {
         try {
            FileOutputStream var4 = new FileOutputStream(var1);
            var4.write(90);
            var4.close();
            var1.delete();
            var3 = true;
         } catch (Exception var26) {
         }
      }

      if (!var3) {
         int var28 = JOptionPane.showConfirmDialog((Component)null, "Danderspritz is unable to write to the user configuration area.\nWithout the ability to write there, several plugins will not work.\nDanderspritz will now change the user configuration area to your temp directory,\nand you will lose any existing customization you have performed.\nSelect 'No' to ignore this problem, or 'Cancel' to change the directory.", "User Configuration is Read-Only", 1, 2, (Icon)null);
         switch(var28) {
         case 0:
            var2 = new File(System.getProperty("java.io.tmpdir"), "UserConfiguration");
         case 1:
         default:
            break;
         case 2:
            return;
         }
      }

      EventQueue.invokeLater(new Runnable() {
         public void run() {
            Start.this.setVisible(false);
            Start.this.dispose();
         }
      });
      String var29 = System.getProperty("com.sun.management.jmxremote.port");
      if (var29 != null) {
         File var5 = new File(this.logField.getText(), String.format("Dump-%s.txt", var29));
         if (!var5.exists()) {
            try {
               FileOutputStream var6 = new FileOutputStream(var5);
               var6.close();
            } catch (Exception var25) {
               Logger.getLogger(Start.class.getName()).log(Level.SEVERE, (String)null, var25);
            }
         }
      }

      setStringDefault("OpsDisk", this.operationField.getText());
      setStringDefault("ResourceDir", this.resourceField.getText());
      setStringDefault("LogDir", this.logField.getText());
      setStringDefault("ConfigDir", this.configurationField.getText());
      setStringDefault("LocalAddress", this.localCommsAddressField.getText());
      if (this.liveOperationThemes.getSelectedItem() != null && !this.liveOperationThemes.getSelectedItem().equals("Default")) {
         setStringDefault(LIVE_KEYWORD, this.liveOperationThemes.getSelectedItem().toString());
      } else {
         setStringDefault(LIVE_KEYWORD, (String)null);
      }

      if (this.replayOperationThemes.getSelectedItem() != null && !this.replayOperationThemes.getSelectedItem().equals("Default")) {
         setStringDefault(REPLAY_KEYWORD, this.replayOperationThemes.getSelectedItem().toString());
      } else {
         setStringDefault(REPLAY_KEYWORD, (String)null);
      }

      setBooleanDefault("OpMode", this.liveOption.isSelected());
      setBooleanDefault("BuildType", this.buildRelease.isSelected());
      setBooleanDefault("GuiType", this.guiRelease.isSelected());
      setBooleanDefault("LocalMode", this.localMode.isSelected());
      setBooleanDefault("LoadPrevious", this.loadPrevious.isSelected());
      setBooleanDefault("thread.dump", this.threadDump.isSelected());
      setBooleanDefault("wait.for.output", this.waitFor.isSelected());

      try {
         userDefaults.store(new FileOutputStream("user.defaults"), "Autogenerated DanderSpritz configuration.  Do not edit manually");
      } catch (Exception var24) {
      }

      ProcessBuilder var30 = new ProcessBuilder(new String[0]);
      Vector var31 = new Vector();
      var30.command(var31);
      String var7 = this.resourceField.getText();
      var31.add(prop.getProperty("java.exe", "java"));
      String[] var8 = prop.getProperty("vmargs", "").split("\\s");
      int var9 = var8.length;

      int var10;
      String var11;
      for(var10 = 0; var10 < var9; ++var10) {
         var11 = var8[var10];
         if (var11.length() > 0) {
            var31.add(var11);
         }
      }

      if (this.guiDebug.isSelected()) {
         var8 = prop.getProperty("vmargs.debug", "").split("\\s");
         var9 = var8.length;

         for(var10 = 0; var10 < var9; ++var10) {
            var11 = var8[var10];
            if (var11.length() > 0) {
               var31.add(var11);
            }
         }
      }

      var31.add(String.format("-Djava.endorsed.dirs=%s/ExternalLibraries/%s/endorsed", var7, "java-j2se_1.6-sun"));
      Vector var32 = new Vector();
      Vector var33 = new Vector();
      this.addJars(var32, new File(String.format("%s/ExternalLibraries/%s", var7, "java-j2se_1.6-sun")));
      var33.add("Ops");
      var33.add(".");
      var33.add(prop.getProperty("res.dir", "Dsz"));
      File[] var34 = (new File(var7)).listFiles();
      int var35 = var34.length;

      int var12;
      File var13;
      for(var12 = 0; var12 < var35; ++var12) {
         var13 = var34[var12];
         if (var13.isDirectory() && !var33.contains(var13.getName())) {
            var33.add(var13.getName());
         }
      }

      Iterator var36 = var33.iterator();

      while(var36.hasNext()) {
         var11 = (String)var36.next();
         File var39 = new File(String.format("%s/%s/Gui", var7, var11));
         if (var39.exists()) {
            var13 = new File(var39, "Config");
            if (var13.exists()) {
               var32.add(var13.getAbsolutePath());
            }

            File var14 = new File(var39, String.format("%s/%s", "lib", "java-j2se_1.6-sun"));
            if (var14.exists()) {
               this.addJars(var32, var14);
            }
         }
      }

      boolean var37 = false;
      if (System.getProperty("os.name").toLowerCase().startsWith(prop.getProperty("windows.start", "win"))) {
         var37 = true;
      }

      URL[] var38 = new URL[var32.size()];

      for(var12 = 0; var12 < var32.size(); ++var12) {
         try {
            var38[var12] = (new File((String)var32.get(var12))).toURI().toURL();
         } catch (MalformedURLException var23) {
            var23.printStackTrace();
         }
      }

      Object var40 = ClassLoader.getSystemClassLoader();
      boolean var41 = false;
      Class var15;
      if (var40 instanceof URLClassLoader) {
         try {
            URLClassLoader var42 = (URLClassLoader)var40;
            var15 = URLClassLoader.class;
            Field var16 = var15.getDeclaredField("ucp");
            var16.setAccessible(true);
            URLClassPath var17 = (URLClassPath)var16.get(var42);
            URL[] var18 = var38;
            int var19 = var38.length;

            for(int var20 = 0; var20 < var19; ++var20) {
               URL var21 = var18[var20];
               var17.addURL(var21);
            }

            var41 = true;
         } catch (Exception var27) {
            var27.printStackTrace();
         }
      }

      if (!var41) {
         var40 = new URLClassLoader(var38, ClassLoader.getSystemClassLoader());
      }

      Vector var43 = new Vector();
      var43.add(String.format("-logDir=%s", this.logField.getText()));
      var43.add(String.format("-resourceDir=%s", var7));
      var43.add(String.format("-comms=%s", this.localCommsAddressField.getText()));
      var43.add(String.format("-build=%s", prop.getProperty(String.format("%s.%s", var37 ? "windows" : "linux", this.buildRelease.isSelected() ? "build.release" : "build.debug"))));
      var43.add(String.format("-local=%s", this.localMode.isSelected() ? "true" : "false"));
      var43.add(String.format("-config=%s", var2.getAbsolutePath()));
      var43.add(String.format("-loadPrevious=%s", this.loadPrevious.isSelected() ? "true" : "false"));
      var43.add(String.format("-threadDump=%s", this.threadDump.isSelected() ? "true" : "false"));
      var15 = null;
      String var44;
      if (this.liveOption.isSelected()) {
         var44 = prop.getProperty("live.operation");
      } else {
         var44 = prop.getProperty("replay.operation");
      }

      if (var37) {
         addLibraryPath(String.format("%s\\ExternalLibraries\\%s", var7, prop.getProperty(String.format("%s.%s", "windows", "tool.chain"))));
      } else {
         addLibraryPath(String.format("%s/ExternalLibraries/%s", var7, prop.getProperty(String.format("%s.%s", "linux", "tool.chain"))));
      }

      System.setProperty("windows.tool.chain", prop.getProperty(String.format("%s.%s", "windows", "tool.chain")));
      System.setProperty("linux.tool.chain", prop.getProperty(String.format("%s.%s", "linux", "tool.chain")));
      if (this.themeSelector.getSelectedItem() != null && !this.themeSelector.getSelectedItem().equals("Default")) {
         System.setProperty("DSZ_KEYWORD", this.themeSelector.getSelectedItem().toString());
      }

      try {
         Thread.currentThread().setContextClassLoader((ClassLoader)var40);
         Class var45 = Class.forName(var44, true, (ClassLoader)var40);
         Method var46 = var45.getMethod("main", String[].class);
         Class var47 = Class.forName("ds.core.DSConstants", true, (ClassLoader)var40);
         Method var48 = var47.getMethod("setLoader", ClassLoader.class);
         var48.invoke((Object)null, var40);
         String[] var49 = new String[var43.size()];
         var49 = (String[])var43.toArray(var49);
         var46.invoke((Object)null, var49);
      } catch (Exception var22) {
         var22.printStackTrace();
         JOptionPane.showMessageDialog((Component)null, "Unable to start DanderSpritz.  The OpsDisk appears incomplete.", "Invalid OpsDisk", 0);
         this.examine();
         this.setVisible(true);
      }

   }

   private void addJars(List<String> var1, File var2) {
      if (var2.isDirectory()) {
         File[] var3 = var2.listFiles(jars);
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            File var6 = var3[var5];
            var1.add(var6.getAbsolutePath());
         }

      }
   }

   private void addJarsRecursively(List<String> var1, File var2) {
      if (var2.isDirectory()) {
         this.addJars(var1, var2);
         File[] var3 = var2.listFiles();
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            File var6 = var3[var5];
            if (var6.isDirectory() && !var6.getName().equals(".svn")) {
               this.addJarsRecursively(var1, var6);
            }
         }

      }
   }

   public boolean isShowOpType() {
      return this.getBooleanProperty("show.optype", true);
   }

   public boolean isShowDebugCore() {
      return this.getBooleanProperty("show.debug.core", false);
   }

   public boolean isShowDebugGui() {
      return this.getBooleanProperty("show.debug.gui", false);
   }

   public boolean isShowLocal() {
      return this.getBooleanProperty("show.local.mode", false);
   }

   public boolean isShowThreadDump() {
      return this.getBooleanProperty("show.thread.dump", false);
   }

   public boolean getBooleanProperty(String var1, Boolean var2) {
      try {
         return Boolean.parseBoolean(prop.getProperty(var1, var2.toString()));
      } catch (Throwable var4) {
         return var2;
      }
   }

   public static void addLibraryPath(String var0) {
      String var1 = null;

      try {
         var1 = (new File(var0)).getCanonicalPath();
      } catch (IOException var8) {
         var8.printStackTrace();
         return;
      }

      try {
         Field var2 = ClassLoader.class.getDeclaredField("usr_paths");
         var2.setAccessible(true);
         String[] var3 = (String[])((String[])var2.get((Object)null));
         String[] var4 = var3;
         int var5 = var3.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            String var7 = var4[var6];
            if (var7.equals(var1)) {
               return;
            }
         }

         var4 = new String[var3.length + 1];
         System.arraycopy(var3, 0, var4, 0, var3.length);
         var4[var3.length] = var1;
         var2.set((Object)null, var4);
      } catch (Throwable var9) {
         var9.printStackTrace();
      }

   }

   private boolean examine() {
      Vector var1 = new Vector();
      Vector var2 = new Vector();
      Vector var3 = new Vector();
      Vector var4 = new Vector();
      var1.add(new File(String.format("%s", this.operationField.getText())));
      var1.add(new File(String.format("%s%s%s", this.operationField.getText(), File.separator, "Bin")));
      var2.add(new File(String.format("%s", this.resourceField.getText())));
      var2.add(new File(String.format("%s/%s/%s/%s/%s", this.resourceField.getText(), File.separator, "Dsz/Gui/lib", "java-j2se_1.6-sun", "Core.jar")));
      boolean var5 = true;
      if (!this.examine(var1, this.operationField)) {
         var5 = false;
      }

      if (!this.examine(var2, this.resourceField)) {
         var5 = false;
      } else {
         this.searchOutThemes(new File(this.resourceField.getText()));
      }

      if (!this.examine(var3, this.configurationField)) {
         var5 = false;
      }

      if (!this.examine(var4, this.logField)) {
         var5 = false;
      }

      if (!this.isValidId(this.localCommsAddressField.getText())) {
         this.setConfig(this.localCommsAddressField, false);
         var5 = false;
      } else {
         this.setConfig(this.localCommsAddressField, true);
      }

      this.goButton.setEnabled(var5);
      return var5;
   }

   private void setConfig(JTextField var1, boolean var2) {
      if (var2) {
         var1.setBackground(Color.WHITE);
         var1.setForeground(Color.BLACK);
      } else {
         var1.setBackground(Color.GRAY);
         var1.setForeground(Color.WHITE);
      }

   }

   private boolean examine(List<File> var1, JTextField var2) {
      boolean var3 = true;
      char[] var4 = INVALIDCHARACTERS;
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         char var7 = var4[var6];
         if (var2.getText().indexOf(var7) >= 0) {
            var3 = false;
            break;
         }
      }

      File var9;
      for(Iterator var8 = var1.iterator(); var8.hasNext(); var3 = var3 && var9.exists()) {
         var9 = (File)var8.next();
      }

      this.setConfig(var2, var3);
      return var3;
   }
}
