/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.dtls.fairdatapoint.service.impl;

import java.util.List;
import javax.xml.datatype.DatatypeConfigurationException;
import nl.dtls.fairdatapoint.domain.StoreManager;
import nl.dtls.fairdatapoint.domain.StoreManagerException;
import nl.dtls.fairdatapoint.service.CatalogMetadata;
import nl.dtls.fairdatapoint.service.DatasetMetadata;
import nl.dtls.fairdatapoint.service.DistributionMetadata;
import nl.dtls.fairdatapoint.service.FDPMetadata;
import nl.dtls.fairdatapoint.service.FairMetaDataService;
import nl.dtls.fairdatapoint.service.FairMetadataServiceException;
import nl.dtls.fairdatapoint.service.MetadataExeception;
import nl.dtls.fairdatapoint.utils.RDFUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openrdf.model.Statement;
import org.openrdf.model.impl.StatementImpl;
import org.openrdf.model.vocabulary.DCTERMS;
import org.openrdf.rio.RDFFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Rajaram Kaliyaperumal
 * @since 2015-12-17
 * @version 0.2
 */
@Service("fairMetaDataServiceImpl")
public class FairMetaDataServiceImpl implements FairMetaDataService {
    private final static Logger LOGGER 
            = LogManager.getLogger(FairMetaDataServiceImpl.class);
    @Autowired
    private StoreManager storeManager;
    
    @Override
    public String retrieveMetaData(String uri, RDFFormat format) 
            throws FairMetadataServiceException {
        
        if(format == null) {
            String errorMsg = "The RDFFormat can't be NULL";
            LOGGER.error(errorMsg);
            throw(new IllegalArgumentException(errorMsg));
        }
        String fdpMetadata = null;
        try {
            List<Statement> statements = 
                    storeManager.retrieveResource(uri);
            if(!statements.isEmpty()) {
                fdpMetadata = RDFUtils.writeToString(statements, format);
            }
        } catch (Exception ex) {
            LOGGER.error("Error retrieving fdp's metadata");
            throw(new FairMetadataServiceException(ex.getMessage()));
        }
        return fdpMetadata;
    }
    
    

    @Override
    public CatalogMetadata retrieveCatalogMetaData(String uri) 
            throws FairMetadataServiceException {
        CatalogMetadata cMetadata = null;
        try {
            List<Statement> statements = 
                    storeManager.retrieveResource(uri);
            cMetadata = new CatalogMetadata(uri, statements);
        } catch (StoreManagerException  ex) {
            LOGGER.error("Error retrieving catalog metadata from the store");
            throw(new FairMetadataServiceException(ex.getMessage()));
        } catch (MetadataExeception | DatatypeConfigurationException ex) {
            LOGGER.error("Error pharsing catalog metadata");
            throw(new FairMetadataServiceException(ex.getMessage()));
        }
        
        return cMetadata;
    }
    
    @Override
    public DatasetMetadata retrieveDatasetMetaData(String uri) 
            throws FairMetadataServiceException {
        DatasetMetadata dMetadata = null;
        try {
            List<Statement> statements = 
                    storeManager.retrieveResource(uri);
            dMetadata = new DatasetMetadata(uri, statements);
        } catch (StoreManagerException  ex) {
            LOGGER.error("Error retrieving dataset metadata from the store");
            throw(new FairMetadataServiceException(ex.getMessage()));
        } catch (MetadataExeception | DatatypeConfigurationException ex) {
            LOGGER.error("Error pharsing dataset metadata");
            throw(new FairMetadataServiceException(ex.getMessage()));
        }        
        return dMetadata;
    }

    @Override
    public DistributionMetadata retrieveDistributionMetaData(String uri) 
            throws FairMetadataServiceException {
        DistributionMetadata distMetadata = null;
        try {
            List<Statement> statements = 
                    storeManager.retrieveResource(uri);
            distMetadata = new DistributionMetadata(uri, statements);
        } catch (StoreManagerException  ex) {
            LOGGER.error(
                    "Error retrieving distribution metadata from the store");
            throw(new FairMetadataServiceException(ex.getMessage()));
        } catch (MetadataExeception | DatatypeConfigurationException ex) {
            LOGGER.error("Error pharsing distribution metadata");
            throw(new FairMetadataServiceException(ex.getMessage()));
        }        
        return distMetadata;
    }
    
    @Override
    public void storeFDPMetaData(FDPMetadata fdpMetaData) 
            throws FairMetadataServiceException {
        if(fdpMetaData == null) {
            String errorMsg = "The fdp metadata can't be NULL";
            LOGGER.error(errorMsg);
            throw(new IllegalArgumentException(errorMsg));
        }
        try {
            storeManager.storeRDF(fdpMetaData.getStatements());
            
        } catch (Exception ex) {
            LOGGER.error("Error storing fdp metadata");
            throw(new FairMetadataServiceException(ex.getMessage()));
        }
    }      
    
    @Override
    public void storeCatalogMetaData(CatalogMetadata catalogMetadata) 
            throws FairMetadataServiceException {
        
        if(catalogMetadata == null) {
            String errorMsg = "The CatalogMetadata can't be NULL";
            LOGGER.error(errorMsg);
            throw(new IllegalArgumentException(errorMsg));
        }
        try {
            Statement stmt = new StatementImpl(catalogMetadata.getFdpUri(), 
                    DCTERMS.MODIFIED, null);
            storeManager.removeStatement(stmt);
            storeManager.storeRDF(catalogMetadata.getStatements());
            
        } catch (Exception ex) {
            LOGGER.error("Error storing catalog metadata");
            throw(new FairMetadataServiceException(ex.getMessage()));
        }
    }
    
    @Override
    public void storeDatasetMetaData(DatasetMetadata datasetMetadata) 
            throws FairMetadataServiceException {
        
        if(datasetMetadata == null) {
            String errorMsg = "The datasetMetadata can't be NULL";
            LOGGER.error(errorMsg);
            throw(new IllegalArgumentException(errorMsg));
        }
        try {
            Statement stmt = new StatementImpl(datasetMetadata.getCatalogURI(), 
                    DCTERMS.MODIFIED, null);
            storeManager.removeStatement(stmt);
            storeManager.storeRDF(datasetMetadata.getStatements());
            
        } catch (Exception ex) {
            LOGGER.error("Error storing dataset metadata");
            throw(new FairMetadataServiceException(ex.getMessage()));
        }
    }

    @Override
    public void storeDistributionMetaData(DistributionMetadata 
            distributionMetadata) throws FairMetadataServiceException {
        if(distributionMetadata == null) {
            String errorMsg = "The distributionMetadata can't be NULL";
            LOGGER.error(errorMsg);
            throw(new IllegalArgumentException(errorMsg));
        }
        try {
            Statement stmt = new StatementImpl(distributionMetadata.
                    getDatasetURI(), DCTERMS.MODIFIED, null);
            storeManager.removeStatement(stmt);
            storeManager.storeRDF(distributionMetadata.getStatements());
            
        } catch (Exception ex) {
            LOGGER.error("Error storing distribution metadata");
            throw(new FairMetadataServiceException(ex.getMessage()));
        }
    }
    
}
