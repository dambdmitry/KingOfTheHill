package edu.lab.server.coodinator.communication.requests;

import edu.lab.server.coodinator.communication.Request;
import edu.lab.server.coodinator.communication.RequestCode;

public class RemoveMeRequest extends Request {
    public RemoveMeRequest(RequestCode code) {
        super(code);
    }

    @Override
    protected void buildInfoBytes() {
        // доп инфы нет
    }
}
