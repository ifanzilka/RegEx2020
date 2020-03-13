package ru.smak.regex

import ru.smak.regex.regex.RegexHelper
import java.awt.Color
import java.awt.Dimension
import java.io.BufferedInputStream
import java.io.File
import java.io.FileInputStream
import java.util.regex.Pattern
import javax.swing.*
import javax.swing.filechooser.FileNameExtensionFilter
import javax.swing.text.BadLocationException
import javax.swing.text.DefaultHighlighter

class MainWindow : JFrame(){

    private val textBlock: JEditorPane
    private val btnFind: JButton
    private val btnReplacement: JButton
    private val btnOpenDialog: JButton
    private val TextReplace :JTextField
    private val Field :JTextField
    private  val emailFind:JButton
    private val btnSubstit: JButton

    init{
        defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        minimumSize = Dimension(500, 500)
        textBlock = JEditorPane()
        btnReplacement= JButton()
        btnReplacement.text=("Заменить")
        btnFind = JButton()
        emailFind=JButton()
        emailFind.text=("email")
        btnFind.text = "Найти "
        btnOpenDialog = JButton()
        btnOpenDialog.text = "Открыть файл"
        btnFind.text = "Найти "

        Field = JTextField("", 25)
        Field.setToolTipText("Длиное поле")

        btnSubstit=JButton()
        btnSubstit.text="Замена Ссылок"
        btnSubstit.addActionListener{
            changeHyperlink()
        }
        TextReplace=JTextField("", 25)

        emailFind.addActionListener{find("([a-z0-9_\\.-]+)@([a-z0-9_\\.-]+)\\.([a-z\\.]{2,6})")}
        btnFind.addActionListener { find(Field.text) }
        val scroll = JScrollPane(
            textBlock,
            ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED
        )
        btnOpenDialog.addActionListener {
            val filefilter = FileNameExtensionFilter("txt", "txt")
            val d = JFileChooser()
            d.isAcceptAllFileFilterUsed = false
            d.fileFilter = filefilter
            d.currentDirectory = File(".")
            d.dialogTitle = "Выберите файл"
            d.approveButtonText = "Выбрать"
            d.addChoosableFileFilter(filefilter)
            d.fileSelectionMode = JFileChooser.FILES_ONLY
            val result = d.showOpenDialog(null)
            if (result == JFileChooser.APPROVE_OPTION) {
                textBlock.text = ""
                val fileInputStream = FileInputStream(d.selectedFile)
                val bufferedInputStream = BufferedInputStream(fileInputStream, 200)
                var i = bufferedInputStream.read()
                do {
                    textBlock.text+=i.toChar()
                    i = bufferedInputStream.read()
                } while (i != -1)



            }
        }
        btnReplacement.addActionListener {

            textBlock.text=ReplaceText(textBlock.text,Field.text,TextReplace.text)


        }


        val gl = GroupLayout(contentPane)
        layout = gl
        gl.setHorizontalGroup(
            gl.createSequentialGroup()
                .addGap(4)
                .addGroup(
                    gl.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addGroup(
                            gl.createSequentialGroup()

                                .addComponent(
                                    btnOpenDialog,
                                    GroupLayout.PREFERRED_SIZE,
                                    GroupLayout.PREFERRED_SIZE,
                                    GroupLayout.PREFERRED_SIZE
                                )
                                .addGap(4)

                        )
                        .addComponent(scroll, 450, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE)
                        .addGroup(
                            gl.createSequentialGroup()
                                .addComponent(
                                    Field
                                )
                                .addGap(4)
                                .addComponent(
                                    btnFind,
                                    GroupLayout.PREFERRED_SIZE,
                                    GroupLayout.PREFERRED_SIZE,
                                    GroupLayout.PREFERRED_SIZE
                                )
                                .addComponent(
                                    emailFind,
                                    GroupLayout.PREFERRED_SIZE,
                                    GroupLayout.PREFERRED_SIZE,
                                    GroupLayout.PREFERRED_SIZE
                                )

                        )
                        .addGap(4)
                        .addGroup(
                            gl.createSequentialGroup()
                                .addComponent(
                                    TextReplace
                                )
                                .addGap(4)
                                .addComponent(
                                    btnReplacement,
                                    GroupLayout.PREFERRED_SIZE,
                                    GroupLayout.PREFERRED_SIZE,
                                    GroupLayout.PREFERRED_SIZE
                                )
                                .addComponent(
                                    btnSubstit,
                                    GroupLayout.PREFERRED_SIZE,
                                    GroupLayout.PREFERRED_SIZE,
                                    GroupLayout.PREFERRED_SIZE
                                )


                        )


                )
                .addGap(4)
        )




        gl.setVerticalGroup(
            gl.createSequentialGroup()
                .addGap(4)
                .addGroup(
                    gl.createParallelGroup()

                        .addComponent(
                            btnOpenDialog,
                            GroupLayout.PREFERRED_SIZE,
                            GroupLayout.PREFERRED_SIZE,
                            GroupLayout.PREFERRED_SIZE
                        )
                )
                .addGap(4)
                .addComponent(scroll, 400, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE)
                .addGap(4)
                .addGap(4)
                .addGroup(
                    gl.createParallelGroup()
                        .addComponent(
                            Field
                        )
                        .addComponent(
                            btnFind,
                            GroupLayout.PREFERRED_SIZE,
                            GroupLayout.PREFERRED_SIZE,
                            GroupLayout.PREFERRED_SIZE
                        )
                        .addComponent(
                            emailFind,
                            GroupLayout.PREFERRED_SIZE,
                            GroupLayout.PREFERRED_SIZE,
                            GroupLayout.PREFERRED_SIZE
                        )

                )
                .addGap(4)

                .addGroup(
                    gl.createParallelGroup()
                        .addComponent(
                            TextReplace
                        )

                        .addComponent(
                           btnReplacement,
                            GroupLayout.PREFERRED_SIZE,
                            GroupLayout.PREFERRED_SIZE,
                            GroupLayout.PREFERRED_SIZE
                        )
                        .addComponent(
                            btnSubstit,
                            GroupLayout.PREFERRED_SIZE,
                            GroupLayout.PREFERRED_SIZE,
                            GroupLayout.PREFERRED_SIZE
                        )

                )

                .addGap(4)

        )


        pack()
    }


    private fun find(str:String) {
        val rh = RegexHelper()


        rh.regex = "([a-z0-9_\\.-]+)@([a-z0-9_\\.-]+)\\.([a-z\\.]{2,6})"
        //rh.regex = "[0-9]+"
       /* var regex="\\d{3}" // шаблон строки из трех цифровых символов;
        if (Field.text.matches() ){
            rh.regex=""


        }*/

        var s =""
        s=s+"("+ Field.text +")"

        rh.regex=str
        var txt = textBlock.text
        txt = txt.replace("\r", "")
        val result = rh.findIn(txt)
        val h = textBlock.highlighter
        val hp = DefaultHighlighter
            .DefaultHighlightPainter(Color.YELLOW)
        h.removeAllHighlights()
        for (res in result){
            try{
                h.addHighlight(res.first, res.second, hp)
            } catch (e: BadLocationException){}
        }
    }
    private fun changeHyperlink() {
        val rh = RegexHelper()
        //rh.regex = "(?:https|http)(?:\\:\\/\\/)(?:[^ ]*)"
        rh.regex = "(https|http)(\\:\\/\\/)([^\\/]*)([^ ]{15})([^ \\n]*)([^ \\n]{5})"
        var txt = textBlock.text
        txt = txt.replace("\r", "")
        val result = rh.findIn(txt)
        var changedText = txt



        try {
            val p=Pattern.compile(rh.regex)
            val m=p.matcher(txt)
            val r =m.replaceAll("$1$2$3***$6")

            textBlock.text=r
        } catch (e: BadLocationException) {
        }

         /*
        for (res in result) {
            try {

                var ft = res.first
                var sd = res.second

               /* val p=Pattern.compile(rh.regex)
                val m=p.matcher(txt)
                val r =m.replaceAll("-$2")*/


                if (sd - ft > 40) {
                    var oldSubstr = txt.substring(ft, sd)
                    var newSubstr = txt.substring(ft, ft + 30) + "***" + txt.substring(sd - 10, sd)
                    changedText = changedText.replace(oldSubstr, newSubstr)
                }
            } catch (e: BadLocationException) {
            }




        }
        textBlock.text = changedText
        val result2 = rh.findIn(changedText)
        val h = textBlock.highlighter
        val hp = DefaultHighlighter
            .DefaultHighlightPainter(Color.YELLOW)
        h.removeAllHighlights()
        for (res in result2) {
            try {
                h.addHighlight(res.first, res.second, hp)
            } catch (e: BadLocationException) {
            }
        }
        */



    }

    private fun ReplaceText(text:String,find:String,Replcae:String):String{

     val rh = RegexHelper()


        //rh.regex = "([a-z0-9_\\.-]+)@([a-z0-9_\\.-]+)\\.([a-z\\.]{2,6})"
        //rh.regex = "[0-9]+"
        /* var regex="\\d{3}" // шаблон строки из трех цифровых символов;
         if (Field.text.matches() ){
             rh.regex=""


         }*/

        val news:String=text.replace(find,Replcae)

        return news
    }
}


