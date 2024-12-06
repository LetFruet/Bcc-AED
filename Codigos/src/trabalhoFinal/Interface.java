package trabalhoFinal;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
public class Interface {

    private JLabel arquivo;
    private JFrame frame;
    private JTextField inputArquivo;
    private JButton btnAnalisar;
    private JTextArea outputArquivo;
    private JTable outputTable;
    private JScrollPane scrollPane;
    private DefaultTableModel tableModel;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Interface window = new Interface(); 	
                    window.frame.setVisible(true);  		
                } catch (Exception e) {
                    e.printStackTrace();  
                }
            }
        });
    }

    public Interface() {
        initialize();  
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0};
        frame.getContentPane().setLayout(gridBagLayout);

        arquivo = new JLabel("Arquivo: ");								
        GridBagConstraints gbc_arquivo = new GridBagConstraints();		
        gbc_arquivo.insets = new Insets(10, 10, 10, 10);  				
        gbc_arquivo.gridx = 0;  gbc_arquivo.gridy = 0;					
        frame.getContentPane().add(arquivo, gbc_arquivo);  				

        inputArquivo = new JTextField(20);  							
        GridBagConstraints gbc_inputArquivo = new GridBagConstraints();	
        gbc_inputArquivo.insets = new Insets(10, 10, 10, 10);			
        gbc_inputArquivo.gridx = 1; gbc_inputArquivo.gridy = 0;			
        gbc_inputArquivo.fill = GridBagConstraints.HORIZONTAL;  		
        frame.getContentPane().add(inputArquivo, gbc_inputArquivo);		

        btnAnalisar = new JButton("Analisar");
        GridBagConstraints gbc_btnAnalisar = new GridBagConstraints();
        gbc_btnAnalisar.insets = new Insets(10, 10, 10, 10);			
        gbc_btnAnalisar.gridx = 2; gbc_btnAnalisar.gridy = 0;			
        frame.getContentPane().add(btnAnalisar, gbc_btnAnalisar);		

        outputArquivo = new JTextArea();										
        outputArquivo.setLineWrap(true); outputArquivo.setWrapStyleWord(true);	
        outputArquivo.setEditable(false);  										
        GridBagConstraints gbc_textArea = new GridBagConstraints();				
        gbc_textArea.insets = new Insets(10, 10, 10, 10);						
        gbc_textArea.fill = GridBagConstraints.BOTH;  							
        gbc_textArea.gridx = 0; gbc_textArea.gridy = 1;							
        gbc_textArea.gridwidth = 3;  											
        gbc_textArea.weightx = 1.0; gbc_textArea.weighty = 1.0;
        frame.getContentPane().add(outputArquivo, gbc_textArea);			

        tableModel = new DefaultTableModel(new Object[][]{}, new String[]{"Tag", "Número de ocorrências"});
        outputTable = new JTable(tableModel);								
        scrollPane = new JScrollPane(outputTable);							
        GridBagConstraints gbc_table = new GridBagConstraints();			
        gbc_table.insets = new Insets(10, 10, 10, 10);					
        gbc_table.fill = GridBagConstraints.BOTH;							
        gbc_table.gridx = 0; gbc_table.gridy = 2;							
        gbc_table.gridwidth = 3;	
        gbc_table.weightx = 1.0; gbc_table.weighty = 1.0;					
        frame.getContentPane().add(scrollPane, gbc_table);	
        
        /*
         *  Caminho dos arquivos para teste:
         *  C:/Users/Leticia/OneDrive - FURB/Docs/Faculdade/BCC/Fase3/AED/TrabalhoFinal/Teste.txt
         *  C:\\PROJETOSAA\\estrutura-dados-BCC\\TrabalhoFinal\\Teste.txt
         */
            
        btnAnalisar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    File arquivo = new File(inputArquivo.getText());       
                    ManipulandoArquivos ma = new ManipulandoArquivos(arquivo);
                    
                    String formatacaoMensagem = ma.verificarFormatacao(ma.pilha);
                    outputArquivo.setText(formatacaoMensagem);
                    
                    tableModel.setRowCount(0);
                    for (int i = 0; i < ma.getNomeTags().length; i++) {
                        if (ma.getNomeTags()[i] != null) {
                            tableModel.addRow(new Object[]{ma.getNomeTags()[i], ma.getContagemTags()[i]});
                        }
                    }
                } catch (FileNotFoundException ex) {
                    outputArquivo.setText("Erro! O arquivo não foi encontrado.");
                } catch (TagsException ex) {
                    outputArquivo.setText(ex.getMessage());
                }
            }
        });
    }
}