package com.ruag.lava;

import hudson.Extension;
import hudson.model.*;

import java.util.List;

import jenkins.model.*;

import org.kohsuke.stapler.export.Exported;
import org.kohsuke.stapler.export.ExportedBean;

/**
 * Expose the data needed for /lava, so it can be exposed by Api.
 * 
 * @author Gian Z'Graggen
 *
 */
@Extension
@Symbol("lava")
@ExportedBean
public class Lava implements UnprotectedRootAction {

	public Api getApi() {
		return new Api(this);
	}

	public boolean checkJobs() {
		Jenkins inst = Jenkins.getInstance();
		List<Item> jobs = inst.getAllItems();
		for (Item job : jobs) {
			Project project = (Project) job;
			AbstractBuild build = project.getLastBuild();
			Result result = build.getResult();
			System.out.println(result);
			if (result == Result.FAILURE) {
				return false;
			}
		}
		return true;
	}

	@Exported
	public boolean isBuildSuccess() {
		System.out.println("----------------------------------------");
		boolean status = checkJobs();
		System.out.println(status);
		return status;
	}

	@Override
	public String getIconFileName() {
		return null;
	}

	@Override
	public String getDisplayName() {
		return "DisplayName: Lava";
	}

	@Override
	public String getUrlName() {
		return "lava";
	}
}




