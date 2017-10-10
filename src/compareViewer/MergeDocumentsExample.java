package compareViewer;

import javax.swing.*;
import javax.swing.text.*;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.*;

import java.lang.reflect.Method;
 
public class MergeDocumentsExample {
    JEditorPane editSource =new JEditorPane();
    JEditorPane editDest =new JEditorPane();
    public MergeDocumentsExample() {
        JFrame frame=new JFrame("Merge document example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new GridBagLayout());
        editSource.setEditorKit(new StyledEditorKit());
        editDest.setEditorKit(new StyledEditorKit());
        frame.getContentPane().add(new JLabel("Source document"), 
             new GridBagConstraints(0,0,1,1,1,0,GridBagConstraints.NORTHWEST,GridBagConstraints.HORIZONTAL, new Insets(5,5,5,5), 0,0));
        frame.getContentPane().add(new JScrollPane(editSource), 
             new GridBagConstraints(0,1,1,1,1,1,GridBagConstraints.NORTHWEST,GridBagConstraints.BOTH, new Insets(5,5,5,5), 0,0));
        frame.getContentPane().add(new JLabel("Merge result document"), 
             new GridBagConstraints(0,2,1,1,1,0,GridBagConstraints.NORTHWEST,GridBagConstraints.HORIZONTAL, new Insets(5,5,5,5), 0,0));
        frame.getContentPane().add(new JScrollPane(editDest), 
             new GridBagConstraints(0,3,1,1,1,1,GridBagConstraints.NORTHWEST,GridBagConstraints.BOTH, new Insets(5,5,5,5), 0,0));
        JPanel p=new JPanel();
        JButton btnMerge=new JButton("Merge documents");
        p.add(btnMerge);
        btnMerge.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    mergeDocument((DefaultStyledDocument)editSource.getDocument(), (DefaultStyledDocument)editDest.getDocument());
                } catch (BadLocationException e1) {
                    e1.printStackTrace();
                }
            }
        });
        frame.getContentPane().add(p, 
            new GridBagConstraints(0,4,1,1,1,0,GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL, new Insets(5,5,5,5), 0,0));
        try {
            SimpleAttributeSet attrs=new SimpleAttributeSet();
            StyledDocument doc=(StyledDocument) editSource.getDocument();
            doc.insertString(0,"Source\ndocument\ncontent",attrs);
            StyleConstants.setBold(attrs, true);
            doc.setCharacterAttributes(9,3,attrs,false);
            attrs=new SimpleAttributeSet();
            StyleConstants.setUnderline(attrs, true);
            doc.setCharacterAttributes(18,3,attrs,false);
 
            attrs=new SimpleAttributeSet();
            StyleConstants.setAlignment(attrs, StyleConstants.ALIGN_CENTER);
            doc.setParagraphAttributes(9,1,attrs,false);
 
            attrs=new SimpleAttributeSet();
            StyleConstants.setAlignment(attrs, StyleConstants.ALIGN_RIGHT);
            doc.setParagraphAttributes(18,1,attrs,false);
 
            doc=(StyledDocument) editDest.getDocument();
            attrs=new SimpleAttributeSet();
            doc.insertString(0,"Target document content and merge results",attrs);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
 
        frame.setSize(500,400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
 
    public static void mergeDocument(DefaultStyledDocument source, DefaultStyledDocument dest) throws BadLocationException {
        ArrayList<DefaultStyledDocument.ElementSpec> specs=new ArrayList<DefaultStyledDocument.ElementSpec>();
        DefaultStyledDocument.ElementSpec spec=new DefaultStyledDocument.ElementSpec(new SimpleAttributeSet(), 
                 DefaultStyledDocument.ElementSpec.EndTagType);
        specs.add(spec);
        fillSpecs(source.getDefaultRootElement(), specs, false);
        spec=new DefaultStyledDocument.ElementSpec(new SimpleAttributeSet(), DefaultStyledDocument.ElementSpec.StartTagType);
        specs.add(spec);
 
        DefaultStyledDocument.ElementSpec[] arr = new DefaultStyledDocument.ElementSpec[specs.size()];
        specs.toArray(arr);
        insertSpecs(dest, dest.getLength(), arr);
    }
 
    protected static void insertSpecs(DefaultStyledDocument doc, int offset, DefaultStyledDocument.ElementSpec[] specs) {
        try {
//            doc.insert(0, specs);  method is protected so we have to
            //extend document or use such a hack
            Method m=DefaultStyledDocument.class.getDeclaredMethod("insert", new Class[] {int.class, DefaultStyledDocument.ElementSpec[].class});
            m.setAccessible(true);
            m.invoke(doc, new Object[] {offset, specs});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    protected static void fillSpecs(Element elem, ArrayList<DefaultStyledDocument.ElementSpec> specs, boolean includeRoot) throws BadLocationException{
        DefaultStyledDocument.ElementSpec spec;
        if (elem.isLeaf()) {
            String str=elem.getDocument().getText(elem.getStartOffset(), elem.getEndOffset()-elem.getStartOffset());
            spec=new DefaultStyledDocument.ElementSpec(elem.getAttributes(), 
                     DefaultStyledDocument.ElementSpec.ContentType,str.toCharArray(), 0, str.length());
            specs.add(spec);
        }
        else {
            if (includeRoot) {
                spec=new DefaultStyledDocument.ElementSpec(elem.getAttributes(), DefaultStyledDocument.ElementSpec.StartTagType);
                specs.add(spec);
            }
            for (int i=0; i<elem.getElementCount(); i++) {
                fillSpecs(elem.getElement(i), specs, true);
            }
 
            if (includeRoot) {
                spec=new DefaultStyledDocument.ElementSpec(elem.getAttributes(), DefaultStyledDocument.ElementSpec.EndTagType);
                specs.add(spec);
            }
        }
    }
 
    public static void main(String[] args) {
        new MergeDocumentsExample();
    }
}