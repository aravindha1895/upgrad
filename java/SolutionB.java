import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

class SiteStats {
	private String url;
	private int numVisits;

	/**
	 * Constructor for the SiteStats class
	 * 
	 * @param url       String that represents an URL that the user has visited
	 * @param numVisits An int that represents the number of times that the user has
	 *                  visited the url
	 */
	public SiteStats(String url, int numVisits) {
		this.url = url;
		this.numVisits = numVisits;
	}

	/**
	 * This method returns the number of times that the user has visited the url.
	 * 
	 * @return An int that represents the number of times that the user has visited
	 *         the url
	 */
	public int getNumVisits() {
		return this.numVisits;
	}

	/**
	 * This method returns the url that we are currently tracking
	 * 
	 * @return A String that represents the url that we are currently tracking
	 */
	public String getUrl() {
		return this.url;
	}

	/**
	 * This method updates the number of times that we have visited the url
	 * 
	 * @param an int that represents the number that we want to set numVisits to
	 */
	public void setNumVisits(int updatedNumVisits) {
		this.numVisits = updatedNumVisits;
	}

	public String toString() {
		return this.url + " | " + this.numVisits;
	}

}

public class SolutionB {

	private static Queue<SiteStats> sites = new LinkedList<SiteStats>();

	// Main method to list top n visited sites
	public static void listTopVisitedSites(Queue<SiteStats> sites, int n) {
		/*
		 * The below loop loops through the queue and sorts such that max visited
		 * web site comes to head of the queue
		 */
		for (int i = 0; i < sites.size(); i++) {
			int maxIndex = getMaximumIndex(sites, sites.size() - i);
			insertMaxmimunIndexToRear(sites, maxIndex, i);
		}
		// After sorting list top n web sites.
		for (int i = 0; i < n; i++) {
			if (!sites.isEmpty()) { // Check if n is > size of the queue to avoid exception
				System.out.println(sites.remove().toString());
			}
		}
	}

	public static int getMaximumIndex(Queue<SiteStats> sites, int indexAfterWhichSorted) {
		int maxValue = -1;// Web site could not be visited -1 time
		int maxIndex = -1;
		for (int i = 0; i < sites.size(); i++) {
			SiteStats currentSiteStat = sites.remove();
			/*
			 * Max visited web sites are stored at the end so max index search should not
			 * go beyond indexAfterWhichSorted, hence the extra condition below.
			 */
			if ((maxValue == -1 || maxValue <= currentSiteStat.getNumVisits()) && i < indexAfterWhichSorted) {
				maxIndex = i;
				maxValue = currentSiteStat.getNumVisits();
			}
			sites.add(currentSiteStat);
		}
		return maxIndex;
	}

	/*
	 * Insert 'NEXT' max visited we site at rear so that at the end max visited
	 * comes to head of queue.
	 */
	public static void insertMaxmimunIndexToRear(Queue<SiteStats> sites, int maxIndex, int n) {
		SiteStats maxIndexSiteStat = null;
		for (int i = 0; i < sites.size(); i++) {
			SiteStats currentSiteStat = sites.remove();
			if (i != maxIndex)
				sites.add(currentSiteStat);
			else { // Store max visited site to temp variable and insert it to end.
				maxIndexSiteStat = currentSiteStat;
			}

		}
		sites.add(sites.remove());
		sites.add(maxIndexSiteStat);
	}

	// Method to find the web site in the queue and increment the visited count by
	// 1, adding new node in case web site is not found
	public static void updateCount(String url) {
		// WRITE CODE HERE
		boolean siteFound = false;
		int i = 0;
		/*  Remove, check if web site present. If present update and add it to end else add it end*/
		while (i < sites.size()) {
			SiteStats currentSiteStat = sites.remove();
			if (currentSiteStat.getUrl().equals(url)) {
				int visitedCount = currentSiteStat.getNumVisits();
				currentSiteStat.setNumVisits(visitedCount + 1); // Increment visit count by 1
				sites.add(currentSiteStat);
				siteFound = true;
				break;
			} else
				sites.add(currentSiteStat);
			i++;
		}
		if (siteFound == false) { // Adding new web site
			SiteStats newSite = new SiteStats(url, 1);
			sites.add(newSite);
		}
	}

	public static void main(String[] args) {
		String[] visitedSites = { "www.google.co.in", "www.google.co.in", "www.facebook.com", "www.upgrad.com",
				"www.google.co.in", "www.youtube.com", "www.facebook.com", "www.upgrad.com", "www.facebook.com",
				"www.google.co.in", "www.microsoft.com", "www.9gag.com", "www.netflix.com", "www.netflix.com",
				"www.9gag.com", "www.microsoft.com", "www.amazon.com", "www.amazon.com", "www.uber.com",
				"www.amazon.com", "www.microsoft.com", "www.upgrad.com" };

		for (String url : visitedSites) {
			updateCount(url);
		}
		
		listTopVisitedSites(sites, 5);

	}

}
