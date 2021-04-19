package edu.lab.server.coodinator.communication.requests;

import edu.lab.server.coodinator.communication.Request;
import edu.lab.server.coodinator.communication.RequestCode;

public class RequestFactory {
    public static Request createRequest(RequestCode code){
        switch (code){
            case REMOVE_ME: return new RemoveMeRequest(code);
            case IM_READY: return new ImReadyRequest(code);
            case GET_TABLE: return new UpdateTableRequest(code);
            default: return null;
        }
    }

    public static Request createRequest(RequestCode code, String info){
        switch (code){
            case REGISTRY: return new RegistryRequest(info);
            case IM_KILL: return new ImKillRequest(info);
            default: return null;
        }
    }
}
