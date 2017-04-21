package net.net16.jeremiahlowe.scicalc.cartesian_plane;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

import net.net16.jeremiahlowe.scicalc.Enums.HorizontalAllignment;
import net.net16.jeremiahlowe.scicalc.Enums.VerticalAllignment;
import net.net16.jeremiahlowe.scicalc.functions.std.BinaryFunction;
import net.net16.jeremiahlowe.scicalc.functions.std.PolarFunction;
import net.net16.jeremiahlowe.scicalc.functions.std.UnaryFunction;
import net.net16.jeremiahlowe.scicalc.utility.GraphicsUtility;
import net.net16.jeremiahlowe.scicalc.utility.MathUtility;
import net.net16.jeremiahlowe.scicalc.utility.collections.Vector2Precise;

//TODO: Fix panning and overshooting bugs
public class CoordinatePlane extends JComponent{
	private static final long serialVersionUID = 1L;
	private CoordinatePlaneGraphics cpg;
	//private Quadrant quadrant = Quadrant.ALL;
	private Vector2Precise viewportSize = new Vector2Precise(3, 3); 
	private Vector2Precise dotSize = new Vector2Precise(0, 0);
	private Vector2Precise unitsPerPixel = new Vector2Precise(0, 0);
	private Vector2Precise panningOffset = new Vector2Precise(0, 0);
	private Vector2Precise tickCounts = new Vector2Precise(1, 1);
	private int lineWidth = 2, arrowTipOffest = 3, tickWidth = 2, surroundingOffset, labelDigits = 2;
	private boolean drawTicks = true, drawGrid = true, labelTicks = true;
	private Color axesColor = Color.BLACK, tickColor = Color.BLACK;
	private Color gridColor = new Color(0, 0, 0, 55);
	private List<Point> points = new ArrayList<Point>();
	private FunctionManager functionManager;
	private boolean ignoreFunctions = false;
	private boolean ignoreFunctionsFlag = false;
	
	public CoordinatePlane() {
		setBackground(Color.WHITE);
		cpg = new CoordinatePlaneGraphics();
		functionManager = new FunctionManager();
		addComponentListener(new ComponentAdapter(){
			@Override
			public void componentResized(ComponentEvent e) {
	            recalculate();                
	        }
		});
		recalculate();
	}
	//[start] Drawing strategy
	@Override
	public void paintComponent(Graphics ug){
		Vector2Precise size = new Vector2Precise(getWidth(), getHeight());
		drawBuffered(ug, size);
	}
	public void drawUnbuffered(Graphics g){
		Vector2Precise size = new Vector2Precise(getWidth(), getHeight());
		drawUnbuffered(g, size);
	}
	private void drawUnbuffered(Graphics g, Vector2Precise size){
		//Start drawing to graphics
		if(drawGrid) drawAxisGrid(g, size, surroundingOffset, lineWidth / 2);
		//Heres where the bulk of time will be spent, this is where ALL points
		//and functions are casted and drawn to the screen
		for(Point p : points) if(p != null) 
			drawPoint(p, g, size, surroundingOffset); //Draw points
		if(!(ignoreFunctions || ignoreFunctionsFlag)) functionManager.drawFunctions(this, g, size); //Draw functions
		else ignoreFunctionsFlag = false;
		//Done! Now we can move on to the less-important stuff
		cpg.drawAxes(this, g, size, axesColor, surroundingOffset, lineWidth);
		if(drawTicks) drawAxisTicks(g, size, surroundingOffset);
	}
	public void draw(Graphics g){
		Vector2Precise size = new Vector2Precise(getWidth(), getHeight());
		drawBuffered(g, size);
	}
	private void drawBuffered(Graphics ug, Vector2Precise size){
		BufferedImage b = new BufferedImage(size.getXI(), size.getYI(), ColorModel.TRANSLUCENT); //Create buffer
		Graphics g = b.getGraphics(); //Get buffer graphics
		g.setColor(getBackground()); //Build background
		g.fillRect(0, 0, size.getXI(), size.getYI());
		//Start drawing
		drawUnbuffered(g, size);
		//Draw buffer to unbuffered (std) graphics
		ug.drawImage(b, 0, 0, size.getXI(), size.getYI(), null);
	}
	//[end]
	private void drawAxisGrid(Graphics g, Vector2Precise size, int surroundingOffset, int lineWidth){
		g.setColor(gridColor);
		//Iterate through negative and positive axises
		for(int i = 0; i < 2; i++){
			//Get a multiplier for later
			int m = i == 0 ? -1 : 1;
			Vector2Precise start = tickCounts.clone(), stop = viewportSize.clone(), inc = tickCounts.clone();
			if(m > 0) stop.translate(panningOffset.clone().negate());
			else stop.translate(panningOffset);
			for(double x = start.x; x < stop.x; x += inc.x){
				//Cast pos. from origin on negator * axis position thus
				//grabbing the pixel location for drawing the tick
				Vector2Precise ca = castFromOrigin(new Vector2Precise(m * x, 0), size, surroundingOffset);
				int x0 = ca.getXI(); // Rounded x position
				//Draw the tick
				if(lineWidth > 1) GraphicsUtility.drawLineWithWidth(g, x0, surroundingOffset, x0, size.getYI() - surroundingOffset, lineWidth);
				else g.drawLine(x0, surroundingOffset, x0, size.getYI() - surroundingOffset);
			}
			for(double y = start.y; y < stop.y; y += inc.y){
				//Cast pos. from origin on negator * axis position thus
				//grabbing the pixel location for drawing the tick
				Vector2Precise ca = castFromOrigin(new Vector2Precise(0, m * y), size, surroundingOffset);
				int y0 = ca.getYI(); // Rounded y position
				//Draw the tick
				if(lineWidth > 1) GraphicsUtility.drawLineWithWidth(g, surroundingOffset, y0, size.getXI() - surroundingOffset, y0, lineWidth);
				else g.drawLine(surroundingOffset, y0, size.getXI() - surroundingOffset, y0);
			}
		}
		if(lineWidth > 1) GraphicsUtility.resetWidth(g);
	}
	private void drawAxisTicks(Graphics g, Vector2Precise size, int surroundingOffset){
		g.setColor(axesColor);
		//Iterate through negative and positive axes
		int fh = g.getFontMetrics().getHeight();
		for(int i = 0; i < 2; i++){
			//Get a multiplier for later
			int m = i == 0 ? -1 : 1;
			Vector2Precise start = tickCounts.clone(), stop = viewportSize.clone(), inc = tickCounts.clone();
			if(m > 0) stop.translate(panningOffset.clone().negate());
			else stop.translate(panningOffset);
			for(double x = start.x; x < stop.x; x += inc.x){
				//Cast pos. from origin on negator * axis position thus
				//grabbing the pixel location for drawing the tick
				Vector2Precise ca = castFromOrigin(new Vector2Precise(m * x, 0), size, surroundingOffset);
				cpg.drawTick(g, size, ca.getXI(), ca.getYI(), surroundingOffset, tickWidth, lineWidth, false);
				int m2 = 1;
				//if(quadrant == Quadrant.I || quadrant == Quadrant.II || quadrant == Quadrant.I_II) m2 = -1;
				if(labelTicks) GraphicsUtility.drawCenteredString(g, MathUtility.numberToString(x * m, labelDigits), ca.getXI(), ca.getYI() + m2 * fh);
			}
			for(double y = start.y; y < stop.y; y += inc.y){
				//Cast pos. from origin on negator * axis position thus
				//grabbing the pixel location for drawing the tick
				Vector2Precise ca = castFromOrigin(new Vector2Precise(0, m * y), size, surroundingOffset);
				cpg.drawTick(g, size, ca.getXI(), ca.getYI(), surroundingOffset, tickWidth, lineWidth, true);
				if(labelTicks){
					String num = MathUtility.numberToString(y * m, labelDigits);
					HorizontalAllignment h = HorizontalAllignment.Left;
					VerticalAllignment v = VerticalAllignment.HalfDown;
					GraphicsUtility.drawAllignedString(g, num, ca.getXI() + surroundingOffset, ca.getYI(), h, v);
				}
			}
		}
		if(lineWidth > 1) GraphicsUtility.resetWidth(g);
	}
	private void drawPoint(Point p, Graphics g, Vector2Precise size, int surroundingOffset){
		g.setColor(p.getColor());
		Vector2Precise v = castFromOrigin(p.position, size, surroundingOffset);
		p.draw(g, v.getXI(), v.getYI(), p.size);
	}
	public Vector2Precise castFromOrigin(Vector2Precise point, Vector2Precise size, int surroundingOffset){
		Vector2Precise out = new Vector2Precise();
		Vector2Precise origin = cpg.getPixelOrigin(size, surroundingOffset);
		if(dotSize.x == 0 || dotSize.y == 0) recalculate();
		//Account for panning (modifying it here kills 1000000 birds with one stone)
		Vector2Precise newPoint = Vector2Precise.add(point, panningOffset);
		//This is 2D, don't over-complicate it! Just simple proportions!
		out.x = newPoint.x * dotSize.x;
		out.x += origin.x; //Casting and then off-setting for the origin
		out.y = -newPoint.y * dotSize.y;
		out.y += origin.y;
		//Returning non rounded values out, left unrounded for more precision
		return out;
	}
	public void recalculate(){recalculate(true);}
	public void recalculate(boolean repaint){
		Vector2Precise size = new Vector2Precise(getWidth(), getHeight());
		dotSize.x = size.x / viewportSize.x;
		dotSize.y = size.y / viewportSize.y;
		unitsPerPixel.x = viewportSize.x / size.x;
		unitsPerPixel.y = viewportSize.y / size.y;
		surroundingOffset = getSurroundingOffset();
		if(repaint) repaint();
	}
	//[start] General stuff
	public void addPoint(Point p){
		points.add(p);
		recalculate();
	}
	public void removePoint(Point p){
		points.remove(p);
		recalculate();
	}
	public void clearPoints(){
		points.clear();
		recalculate();
	}
	public void addFunction(UnaryFunction f){functionManager.add(f);}
	public void addFunction(BinaryFunction f){functionManager.add(f);}
	public void addFunction(PolarFunction f){functionManager.add(f);}
	public void removeFunction(UnaryFunction f){functionManager.remove(f);}
	public void removeFunction(BinaryFunction f){functionManager.remove(f);}
	public void removeFunction(PolarFunction f){functionManager.remove(f);}
	public void clearUnaryFunctions(){functionManager.unaryFunctions.clear();}
	public void clearBinaryFunctions(){functionManager.binaryFunctions.clear();}
	public void clearAllFunctions(){clearBinaryFunctions(); clearUnaryFunctions();}
	public void setViewportSize(double x, double y){
		viewportSize = new Vector2Precise(x, y);
		recalculate();
	}
	public Vector2Precise getDotSize(){
		recalculate(false);
		return dotSize.clone();
	}
	public Vector2Precise getUnitsPerPixel(){
		recalculate(false);
		return unitsPerPixel.clone();
	}
	public void recalculateIgnoreFunctions(){
		ignoreFunctionsFlag = true;
		recalculate();
	}
	public void setIgnoreFunctions(boolean enabled){ignoreFunctions = enabled;}
	public boolean ignoreFunctions(){return ignoreFunctions;}
	public void setViewportSize(Vector2Precise size){setViewportSize(size.x, size.y);}
	public Vector2Precise getViewportSize(){return viewportSize.clone();}
	public int getLineWidth() {return lineWidth;}
	public void setLineWidth(int lineWidth) {this.lineWidth = lineWidth;}
	public int getArrowTipOffest() {return arrowTipOffest;}
	public void setArrowTipOffest(int arrowTipOffest) {this.arrowTipOffest = arrowTipOffest;}
	public int getTickWidth() {return tickWidth;}
	public void setTickWidth(int tickWidth) {this.tickWidth = tickWidth;}
	public boolean drawTicks() {return drawTicks;}
	public void setDrawTicks(boolean drawTicks) {this.drawTicks = drawTicks;}
	public boolean drawGrid() {return drawGrid;}
	public void setDrawGrid(boolean drawGrid) {this.drawGrid = drawGrid;}
	public Color getAxesColor() {return axesColor;}
	public void setAxesColor(Color axesColor) {this.axesColor = axesColor;}
	public Color getTickColor() {return tickColor;}
	public void setTickColor(Color tickColor) {this.tickColor = tickColor;}
	public Color getGridColor() {return gridColor;}
	public void setGridColor(Color gridColor) {this.gridColor = gridColor;}
	public int getSurroundingOffset() {return arrowTipOffest + ((lineWidth > 1) ? 6 : 0);}
	public Vector2Precise getTickCounts(){return tickCounts.clone();}
	public void setTickCounts(Vector2Precise to){
		tickCounts = to.clone();
		recalculate();
	}
	public int getAxesLabelDigits(){return labelDigits;}
	public void setAxesLabelDigits(int digits){
		labelDigits = digits;
		recalculate();
	}
	public boolean getAxesLabelsEnabled(){return labelTicks;}
	public void setAxesLabels(boolean enabled){
		labelTicks = enabled;
		recalculate();
	}
	public CoordinatePlaneGraphics getCPG(){return cpg;}
	public FunctionManager getFM(){return functionManager;}
	public Vector2Precise getOriginPanningOffset(){return panningOffset.clone();}
	public void setOriginPanningOffset(Vector2Precise to){
		panningOffset = to.clone();
		recalculate();
	}
	public void setOriginPanningOffset(double x, double y){setOriginPanningOffset(new Vector2Precise(x, y));}
	public void pan(Vector2Precise by){pan(by.x, by.y);}
	public void pan(double x, double y){setOriginPanningOffset(panningOffset.x + x, panningOffset.y + y);}
	public Vector2Precise getPlaneDomain() {return cpg.getPlaneDomain(getViewportSize(), getOriginPanningOffset());}
	public Vector2Precise getPlaneRange() {return cpg.getPlaneRange(getViewportSize(), getOriginPanningOffset());}
}
