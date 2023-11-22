package org.kainos.ea.api;

import org.kainos.ea.cli.Capability;
import org.kainos.ea.cli.JobBand;
import org.kainos.ea.db.CapabilityDao;
import org.kainos.ea.db.DatabaseConnector;
import org.kainos.ea.db.JobBandDao;
import org.kainos.ea.exception.FailedToGetCapabilitiesException;
import org.kainos.ea.exception.FailedToGetJobBandsException;
import org.kainos.ea.exception.FailedToGetJobsException;

import java.sql.SQLException;
import java.util.List;

public class JobBandService {

    private final JobBandDao jobBandDao;
    private final DatabaseConnector databaseConnector;

    public JobBandService(JobBandDao jobBandDao, DatabaseConnector databaseConnector) {
        this.jobBandDao = jobBandDao;
        this.databaseConnector = databaseConnector;
    }

    public List<JobBand> getAllJobBands() throws FailedToGetJobBandsException, SQLException {
        return jobBandDao.getAllJobBands(databaseConnector.getConnection());
    }

}
