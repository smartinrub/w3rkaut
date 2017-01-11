package net.dynu.w3rkaut.domain.respository;

import net.dynu.w3rkaut.domain.model.Location;
import net.dynu.w3rkaut.network.model.RESTLocation;

import java.util.List;

public interface LocationRepository {

    List<RESTLocation> getAll();
    String insert(RESTLocation location);
}