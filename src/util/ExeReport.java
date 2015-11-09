/**
 * 
 */
package util;

/**
 * Class for Execution Report.
 * @author pennlio
 *
 */
public class ExeReport extends ACK {
	
	/**
	 *  Broker capacity in order execution, 1 for agent.
	 */
	int lastCapacity;
	
	public ExeReport() {
		super();
		this.lastCapacity = 1; // for agent
	}

	public int getLastCapacity() {
		return lastCapacity;
	}

	public void setLastCapacity(int lastCapacity) {
		this.lastCapacity = lastCapacity;
	}
}
