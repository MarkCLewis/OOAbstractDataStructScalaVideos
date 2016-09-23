package multithreading.drawing

import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.control.Button
import scalafx.scene.shape.Rectangle
import scalafx.scene.paint.Color
import scalafx.scene.control.MenuBar
import scalafx.scene.control.Menu
import scalafx.scene.control.MenuItem
import scalafx.scene.control.SeparatorMenuItem
import scalafx.scene.layout.BorderPane
import scalafx.scene.control.TabPane
import scalafx.scene.control.Tab
import scalafx.scene.control.SplitPane
import scalafx.geometry.Orientation
import scalafx.scene.control.TreeView
import scalafx.scene.control.ScrollPane
import scalafx.scene.control.Slider
import scalafx.scene.canvas.Canvas
import scalafx.scene.control.TextField
import scalafx.scene.control.TextArea
import scalafx.event.ActionEvent
import scalafx.scene.control.Label
import scalafx.scene.control.ChoiceDialog
import scalafx.scene.control.TreeItem
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scalafx.application.Platform

object DrawingMain extends JFXApp {
  private var drawings = List[(Drawing, TreeView[Drawable])]()
  
  private val creators = Map[String, Drawing => Drawable](
      "Rectangle" -> (d => new DrawRectangle(d, 0, 0, 100, 100, Color.Black)),
      "Transform" -> (d => new DrawTransform(d)),
      "Text" -> (d => new DrawText(d, 0, 20, "Text", Color.Black)),
      "Maze" -> (d => new DrawMaze(d))
      )

  stage = new JFXApp.PrimaryStage {
    title = "Drawing Program"
    scene = new Scene(800, 600) {
      val menuBar = new MenuBar
      val fileMenu = new Menu("File")
      val newItem = new MenuItem("New")
      val openItem = new MenuItem("Open")
      val saveItem = new MenuItem("Save")
      val exitItem = new MenuItem("Exit")
      fileMenu.items = List(newItem, openItem, saveItem, new SeparatorMenuItem, exitItem)
      val editMenu = new Menu("Edit")
      val addItem = new MenuItem("Add Drawable")
      val copyItem = new MenuItem("Copy")
      val cutItem = new MenuItem("Cut")
      val pasteItem = new MenuItem("Paste")
      editMenu.items = List(addItem, copyItem, cutItem, pasteItem)
      menuBar.menus = List(fileMenu, editMenu)

      val tabPane = new TabPane
      val firstDrawing = new Drawing
      val (tab, tree) = makeDrawingTab(firstDrawing, "Untitled")
      drawings = drawings :+ firstDrawing -> tree
      tabPane += tab

      newItem.onAction = (ae: ActionEvent) => {
        val newDrawing = new Drawing
        val (tab, tree) = makeDrawingTab(newDrawing, "Untitled")
        drawings = drawings :+ newDrawing -> tree
        tabPane += tab
      }
      addItem.onAction = (ae: ActionEvent) => {
        val current = tabPane.selectionModel().selectedIndex()
        if (current >= 0) {
          val (drawing, treeView) = drawings(current)
          val dtypes = creators.keys.toSeq
          val dialog = new ChoiceDialog(dtypes(0), dtypes)
          dialog.showAndWait().foreach { s =>
            val d = creators(s)(drawing)
            val treeSelect = treeView.selectionModel().selectedItem()
            def treeAdd(item: TreeItem[Drawable]): Unit = item.getValue match {
              case dt: DrawTransform =>
                dt.addChild(d)
                item.children += new TreeItem(d)
                drawing.draw()
              case d =>
                treeAdd(item.getParent)
            }
            if (treeSelect != null) treeAdd(treeSelect)
          }
        }
      }
      exitItem.onAction = (ae: ActionEvent) => {
        // TODO Save all the tabs
        sys.exit(0)
      }

      val rootPane = new BorderPane
      rootPane.top = menuBar
      rootPane.center = tabPane
      root = rootPane
    }
  }

  private def makeDrawingTab(drawing: Drawing, name: String): (Tab, TreeView[Drawable]) = {
    val drawingTree = new TreeView[Drawable]
    drawingTree.root = drawing.makeTree()
    val treeScroll = new ScrollPane
    drawingTree.prefWidth <== treeScroll.width
    drawingTree.prefHeight <== treeScroll.height
    treeScroll.content = drawingTree
    val propertyPane = new ScrollPane
    val leftSplit = new SplitPane
    leftSplit.orientation = Orientation.Vertical
    leftSplit.items ++= List(treeScroll, propertyPane)

    val topRightBorder = new BorderPane
    val slider = new Slider(0, 1000, 0)
    val canvas = new Canvas(2000, 2000)
    val gc = canvas.graphicsContext2D
    drawing.gc = gc
    val scrollCanvas = new ScrollPane
    scrollCanvas.content = canvas
    topRightBorder.top = slider
    topRightBorder.center = scrollCanvas
    val bottomRightBorder = new BorderPane
    val commandField = new TextField
    val commandArea = new TextArea
    commandArea.editable = false
    bottomRightBorder.top = commandField
    bottomRightBorder.center = commandArea
    val rightSplit = new SplitPane
    rightSplit.orientation = Orientation.Vertical
    rightSplit.items ++= List(topRightBorder, bottomRightBorder)
    rightSplit.dividerPositions = 0.7
    
    // Handle commands
    commandField.onAction = (ae: ActionEvent) => {
      val command = commandField.text()
      val result = Future { Commands(command, drawing) }
      result.foreach { r =>
        Platform.runLater(commandArea.appendText("> "+command+"\n"+r+"\n"))
      }
      commandField.text = ""
    }

    val topSplit = new SplitPane
    topSplit.items ++= List(leftSplit, rightSplit)
    topSplit.dividerPositions = 0.3

    drawingTree.selectionModel.value.selectedItem.onChange {
      val selected = drawingTree.selectionModel().selectedItem()
      if (selected != null) {
        propertyPane.content = selected.getValue.propertiesPanel
      } else {
        propertyPane.content = new Label("Nothing selected.")
      }
    }

    val tab = new Tab
    tab.text = name
    tab.content = topSplit
    tab -> drawingTree
  }

  private[drawing] def labeledTextField(labelString: String, initialText: String, action: String => Unit): BorderPane = {
    val borderPane = new BorderPane
    borderPane.left = new Label(labelString)
    val textField = new TextField
    textField.text = initialText
    borderPane.center = textField
    textField.onAction = (ae: ActionEvent) => action(textField.text.value)
    textField.focused.onChange(if (!textField.focused.value) action(textField.text.value))
    borderPane
  }
}