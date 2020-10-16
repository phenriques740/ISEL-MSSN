package T1;

public class SubPlot {
	
	private float[] window;
	private float[] viewport;
	private float mx;
	private float bx;
	private float my;
	private float by;

	public SubPlot(float[] window, float[] viewport, float fullwidth, float fullheight)
	{
		this.window = window;
		this.viewport = viewport;
		mx = viewport[2] * fullwidth / (window[1] - window[0]);
		bx = viewport[0] * fullwidth;
		my = -viewport[3] * fullheight / (window[3] - window[2]);
		by = (1 - viewport[1]) * fullheight;
	}
	
	public float[] getPixelCoord(float x, float y)
	{
		float[] coord = new float[2];
		
		coord[0] = bx + mx * (x - window[0]);
		coord[1] = by + my * (y - window[2]);
		
		return coord;
	}
	
	public float[] getWorldCoord(float xx, float yy)
	{
		float[] coord = new float[2];
		
		coord[0] = window[0] + (xx - bx) / mx;
		coord[1] = window[2] + (yy - by) / my;
		
		return coord;
	}

	public boolean isInside(float xx, float yy)
	{
		float[] c = getWorldCoord(xx, yy);
		return (c[0] >= window[0] && c[0] <= window[1] && 
				c[1] >= window[2] && c[1] <= window[3]);
	}
	
	public float[] getBoundingBox()
	{
		float[] c1 = getPixelCoord(window[0], window[2]);
		float[] c2 = getPixelCoord(window[1], window[3]);	
		float[] box = {c1[0], c1[1], c2[0] - c1[0], c2[1] - c1[1]};
		
		return box;
	}
	
	public float[] getBox(float cx, float cy, float dimx, float dimy)
	{
		float[] c1 = getPixelCoord(cx, cy);
		float[] c2 = getPixelCoord(cx + dimx, cy + dimy);
		float[] box = {c1[0], c1[1], c2[0] - c1[0], c2[1] - c1[1]};
		
		return box;
	}
	
	public float[] getBox(float[] b)
	{
		float[] c1 = getPixelCoord(b[0], b[1]);
		float[] c2 = getPixelCoord(b[0] + b[2], b[1] + b[3]);
		float[] box = {c1[0], c1[1], c2[0] - c1[0], c2[1] - c1[1]};
		
		return box;
	}
		
	public float[] getViewport() {
		return viewport;
	}

	public void setViewport(float[] viewport) {
		this.viewport = viewport;
	}
	
	public float[] getWindow() {
		return window;
	}

	public void setWindow(float[] window) {
		this.window = window;
	}
	
	
}





