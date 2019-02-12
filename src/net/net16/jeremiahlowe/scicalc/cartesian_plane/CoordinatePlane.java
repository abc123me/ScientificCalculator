package net.net16.jeremiahlowe.scicalc.cartesian_plane;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

import net.net16.jeremiahlowe.scicalc.Enums.HorizontalAllignment;
import net.net16.jeremiahlowe.scicalc.Enums.VerticalAllignment;
import net.net16.jeremiahlowe.scicalc.functions.std.BinaryFunction;
import net.net16.jeremiahlowe.scicalc.functions.std.PolarFunction;
import net.net16.jeremiahlowe.scicalc.functions.std.UnaryFunction;
import net.net16.jeremiahlowe.scicalc.utility.GraphicsUtility;
import net.net16.jeremiahlowe.shared.TextUtility;
import net.net16.jeremiahlowe.shared.math.Vector;
import net.net16.jeremiahlowe.shared.math.Viewport;

//TODO: Fix panning and overshooting bugs
public class CoordinatePlane extends JComponent{
	private static final long serialVersionUID = 1L;
	private CoordinatePlaneGraphics cpg;
	private List<PlanePaintHandler> paintHandlers;
	private Viewport viewport = new Viewport(10, 10);
	private Vector dotSize = new Vector(0, 0);
	private Vector unitsPerPixel = new Vector(0, 0);
	private Vector tickCount = new Vector(10, 10);
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
		paintHandlers = new ArrayList<PlanePaintHandler>();
		functionManager = new FunctionManager();
		surroundingOffset = calcSurroundingOffset();
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
		Vector size = new Vector(getWidth(), getHeight());
		drawBuffered(ug, size);
	}
	public void drawUnbuffered(Graphics g){
		Vector size = new Vector(getWidth(), getHeight());
		drawUnbuffered(g, size);
	}
	private void drawUnbuffered(Graphics g, Vector pixelSize){
		//Start drawing to graphics
		Vector inc = Vector.div(viewport.getSize(), tickCount);
		for(PlanePaintHandler pph : paintHandlers) pph.prePaint(this, g); //Draw paint handlers
		if(drawGrid) drawAxisGrid(g, inc, pixelSize, surroundingOffset, lineWidth / 2);
		//Heres where the bulk of time will be spent, this is where ALL points
		//and functions are casted and drawn to the screen
		for(Point p : points) if(p != null) 
			drawPoint(p, g, pixelSize, surroundingOffset); //Draw points
		if(!(ignoreFunctions || ignoreFunctionsFlag))
			functionManager.drawFunctions(this, g, pixelSize); //Draw functions
		else
			ignoreFunctionsFlag = false;
		for(PlanePaintHandler pph : paintHandlers) pph.paint(this, g); //Draw paint handlers
		//Done! Now we can move on to the less-important stuff
		cpg.drawAxes(this, g, pixelSize, axesColor, surroundingOffset, lineWidth);
		if(drawTicks) drawAxisTicks(g, inc, pixelSize, surroundingOffset);
		for(PlanePaintHandler pph : paintHandlers) pph.postPaint(this, g); //Draw paint handlers
	}
	public void draw(Graphics g){
		Vector size = new Vector(getWidth(), getHeight());
		drawBuffered(g, size);
	}
	private void drawBuffered(Graphics ug, Vector size){
		BufferedImage buffer = new BufferedImage(size.getXInt(), size.getYInt(), ColorModel.TRANSLUCENT); //Create buffer
		Graphics g = buffer.getGraphics(); //Get buffer graphics
		g.setColor(getBackground()); //Build background
		g.fillRect(0, 0, size.getXInt(), size.getYInt());
		//Start drawing
		drawUnbuffered(g, size);
		//Draw buffer to unbuffered (std) graphics
		ug.drawImage(buffer, 0, 0, size.getXInt(), size.getYInt(), null);
	}
	public void takeScreenshot(String fileName, String format) throws Exception{
		Vector size = new Vector(getWidth(), getHeight());
		File imageFile = new File(fileName);
		imageFile.createNewFile();
		BufferedImage buffer = new BufferedImage(size.getXInt(), size.getYInt(), ColorModel.TRANSLUCENT);
		Graphics g = buffer.getGraphics(); //Get buffer graphics
		g.setColor(getBackground()); //Build background
		g.fillRect(0, 0, size.getXInt(), size.getYInt());
		drawUnbuffered(g, size);
		ImageIO.write(buffer, format, imageFile);
	}
	//[end]
	private void drawAxisGrid(Graphics g, Vector inc, Vector size, int surroundingOffset, int lineWidth){
		g.setColor(gridColor);
		//Iterate through negative and positive axises
		for(int m = -1; m < 2; m += 2){
			Vector start = new Vector(0), stop = viewport.getSize();
			if(m > 0) stop.translate(viewport.getOffset().negate());
			else stop.translate(viewport.getOffset());
			for(float x = start.x; x < stop.x; x += inc.x){
				if(x == 0) continue;
				//Cast pos. from origin on negator * axis position thus
				//grabbing the pixel location for drawing the tick
				Vector ca = castFromOrigin(new Vector(m * x, 0), size, surroundingOffset);
				int x0 = ca.getXInt(); // Rounded x position
				//Draw the tick
				if(lineWidth > 1) GraphicsUtility.drawLineWithWidth(g, x0, surroundingOffset, x0, size.getYInt() - surroundingOffset, lineWidth);
				else g.drawLine(x0, surroundingOffset, x0, size.getYInt() - surroundingOffset);
			}
			for(float y = start.y; y < stop.y; y += inc.y){
				if(y == 0) continue;
				//Cast pos. from origin on negator * axis position thus
				//grabbing the pixel location for drawing the tick
				Vector ca = castFromOrigin(new Vector(0, m * y), size, surroundingOffset);
				int y0 = ca.getYInt(); // Rounded y position
				//Draw the tick
				if(lineWidth > 1) GraphicsUtility.drawLineWithWidth(g, surroundingOffset, y0, size.getXInt() - surroundingOffset, y0, lineWidth);
				else g.drawLine(surroundingOffset, y0, size.getXInt() - surroundingOffset, y0);
			}
		}
		if(lineWidth > 1) GraphicsUtility.resetWidth(g);
	}
	private void drawAxisTicks(Graphics g, Vector inc, Vector size, int surroundingOffset){
		g.setColor(axesColor);
		//Iterate through negative and positive axes
		int fh = g.getFontMetrics().getHeight();
		for(int m = -1; m < 2; m += 2){
			Vector start = new Vector(0), stop = viewport.getSize();
			if(m > 0) stop.translate(viewport.getOffset().copy().negate());
			else stop.translate(viewport.getOffset());
			for(float x = start.x; x < stop.x; x += inc.x){
				if(x == 0) continue;
				//Cast pos. from origin on negator * axis position thus
				//grabbing the pixel location for drawing the tick
				Vector ca = castFromOrigin(new Vector(m * x, 0), size, surroundingOffset);
				cpg.drawTick(g, size, ca.getXInt(), ca.getYInt(), surroundingOffset, tickWidth, lineWidth, false);
				int m2 = 1;
				//if(quadrant == Quadrant.I || quadrant == Quadrant.II || quadrant == Quadrant.I_II) m2 = -1;
				if(labelTicks) GraphicsUtility.drawCenteredString(g, TextUtility.numberToString(x * m, labelDigits), ca.getXInt(), ca.getYInt() + m2 * fh);
			}
			for(float y = start.y; y < stop.y; y += inc.y){
				if(y == 0) continue;
				//Cast pos. from origin on negator * axis position thus
				//grabbing the pixel location for drawing the tick
				Vector ca = castFromOrigin(new Vector(0, m * y), size, surroundingOffset);
				cpg.drawTick(g, size, ca.getXInt(), ca.getYInt(), surroundingOffset, tickWidth, lineWidth, true);
				if(labelTicks){
					String num = TextUtility.numberToString(y * m, labelDigits);
					HorizontalAllignment h = HorizontalAllignment.Left;
					VerticalAllignment v = VerticalAllignment.HalfDown;
					GraphicsUtility.drawAllignedString(g, num, ca.getXInt() + surroundingOffset, ca.getYInt(), h, v);
				}
			}
		}
		if(lineWidth > 1) GraphicsUtility.resetWidth(g);
	}
	private void drawPoint(Point p, Graphics g, Vector size, int surroundingOffset){
		g.setColor(p.getColor());
		Vector v = castFromOrigin(p.position, size, surroundingOffset);
		p.draw(g, v.getXInt(), v.getYInt(), p.size);
	}
	public Vector castSize(Vector s) {
		Vector out = new Vector();
		Vector size = new Vector(getWidth(), getHeight());
		out.x = (s.x / viewport.w) * size.x;
		out.y = (s.y / viewport.h) * size.y;
		return out;
	}
	public Vector castPosition(Vector p) {
		Vector size = new Vector(getWidth(), getHeight());
		return castFromOrigin(p, size, getSurroundingOffset());
	}
	protected Vector castFromOrigin(Vector point, Vector size, int surroundingOffset){
		Vector out = new Vector();
		Vector origin = cpg.getPixelOrigin(size, surroundingOffset);
		if(dotSize.x == 0 || dotSize.y == 0) recalculate();
		//Account for panning (modifying it here kills 1000000 birds with one stone)
		Vector newPoint = Vector.add(point, viewport.getOffset());
		//This is 2D, don't over-complicate it! Just simple proportions!
		out.x = newPoint.x * dotSize.x;
		out.x += origin.x; //Casting and then off-setting for the origin
		out.y = -newPoint.y * dotSize.y;
		out.y += origin.y;
		return out;
	}
	public void recalculate(){recalculate(true);}
	public void recalculate(boolean repaint){
		Vector size = new Vector(getWidth(), getHeight());
		dotSize.x = size.x / viewport.w;
		dotSize.y = size.y / viewport.h;
		unitsPerPixel.x = viewport.w / size.x;
		unitsPerPixel.y = viewport.h / size.y;
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
	public void clearFunctions(){functionManager.clear();}
	public Vector getDotSize(){
		recalculate(false);
		return dotSize.copy();
	}
	public Vector getPixelSize(){
		recalculate(false);
		return unitsPerPixel.copy();
	}
	public void recalculateIgnoreFunctions(){
		ignoreFunctionsFlag = true;
		recalculate();
	}
	public void setIgnoreFunctions(boolean enabled){ignoreFunctions = enabled;}
	public boolean ignoreFunctions(){return ignoreFunctions;}
	public void setViewportSize(Vector size){setViewportSize(size.x, size.y);}
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
	public int calcSurroundingOffset() {return arrowTipOffest + ((lineWidth > 1) ? 6 : 0);}
	public Vector getTickCount(){return tickCount.copy();}
	public void setTickCount(Vector to){
		if(to.x <= 0 || to.y <= 0)
			throw new RuntimeException("Invalid tick count: " + to);
		tickCount = to.copy();
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
	public void addPaintHandler(PlanePaintHandler pph){paintHandlers.add(pph);}
	public void removePaintHandler(PlanePaintHandler pph){paintHandlers.remove(pph);}
	public List<PlanePaintHandler> getPaintHandlerList(){return paintHandlers;}
	public void setSurroundingOffset(int surroundingOffset) {this.surroundingOffset = surroundingOffset;}
	public int getSurroundingOffset() {return surroundingOffset;}
	
	public Vector getViewportSize(){return viewport.getSize();}
	public void setViewportSize(float x, float y){
		viewport.w = x;
		viewport.h = y;
		recalculate();
	}
	public void pan(Vector by){
		viewport.translate(by);
		recalculate();
	}
	public void pan(float x, float y){pan(new Vector(x, y));}
	public Vector getPlaneDomain() {
		return cpg.getPlaneDomain(viewport.getSize(), viewport.getOffset());
	}
	public Vector getPlaneRange() {
		return cpg.getPlaneRange(viewport.getSize(), viewport.getOffset());
	}
	public Vector getViewportOffset() {
		return viewport.getOffset();
	}
	public Viewport getViewport() {
		return viewport;
	}
	public void setViewportOffset(Vector pos) {
		viewport.x = pos.x;
		viewport.y = pos.y;
		recalculate();
	}
	public void setViewportOffset(float x, float y) {
		viewport.x = x;
		viewport.y = y;
		recalculate();
	}
	public Runnable getRepaintRunnable() {
		return new Runnable() {
			@Override
			public void run() {
				recalculate(true);
			}
		};
	}
}
