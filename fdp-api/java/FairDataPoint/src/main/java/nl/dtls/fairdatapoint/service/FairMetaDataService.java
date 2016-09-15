/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.dtls.fairdatapoint.service;

import nl.dtl.fairmetadata.io.MetadataException;
import nl.dtl.fairmetadata.model.CatalogMetadata;
import nl.dtl.fairmetadata.model.DatasetMetadata;
import nl.dtl.fairmetadata.model.DistributionMetadata;
import nl.dtl.fairmetadata.model.FDPMetadata;


/**
 *
 * @author Rajaram Kaliyaperumal
 * @since 2015-11-23
 * @version 0.2
 */
public interface FairMetaDataService {   
        
    /**
     * Get metadata of given fdp URI 
     * 
     * @param uri fdp URI
     * @return FDPMetadata object
     * @throws FairMetadataServiceException 
     */
    FDPMetadata retrieveFDPMetaData(String uri) 
            throws FairMetadataServiceException; 
    
    
    /**
     * Get metadata of given catalog URI 
     * 
     * @param uri catalog URI
     * @return CatalogMetadata object
     * @throws FairMetadataServiceException 
     */
    CatalogMetadata retrieveCatalogMetaData(String uri) 
            throws FairMetadataServiceException; 
    
    /**
     * Get metadata of given dataset URI 
     * 
     * @param uri dataset URI
     * @return DatasetMetadata object
     * @throws FairMetadataServiceException 
     */
    DatasetMetadata retrieveDatasetMetaData(String uri) 
            throws FairMetadataServiceException;  
    
    /**
     * Get metadata of given distribution URI 
     * 
     * @param uri distribution URI
     * @return DistributionMetadata object
     * @throws FairMetadataServiceException 
     */
    DistributionMetadata retrieveDistributionMetaData(String uri) 
            throws FairMetadataServiceException;  
    /**
     * Store catalog metadata
     * 
     * @param catalogMetadata
     * @throws FairMetadataServiceException 
     * @throws nl.dtl.fairmetadata.io.MetadataException 
     */
    void storeCatalogMetaData(CatalogMetadata catalogMetadata) 
            throws FairMetadataServiceException, MetadataException;
    /**
     * Store dataset metadata
     * 
     * @param datasetMetadata
     * @throws FairMetadataServiceException 
     * @throws nl.dtl.fairmetadata.io.MetadataException 
     */
    void storeDatasetMetaData(DatasetMetadata datasetMetadata) 
            throws FairMetadataServiceException, MetadataException;
    
    /**
     * Store fdp metadata
     * 
     * @param fdpMetaData
     * @throws FairMetadataServiceException 
     * @throws nl.dtl.fairmetadata.io.MetadataException 
     */
    void storeFDPMetaData(FDPMetadata fdpMetaData) 
            throws FairMetadataServiceException, MetadataException; 
    
    /**
     * Store distribution metadata
     * 
     * @param distributionMetadata
     * @throws nl.dtls.fairdatapoint.service.FairMetadataServiceException
     * @throws nl.dtl.fairmetadata.io.MetadataException
     */
    void storeDistributionMetaData(DistributionMetadata distributionMetadata)
            throws FairMetadataServiceException, MetadataException;
        
}
