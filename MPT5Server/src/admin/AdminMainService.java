package admin;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class AdminMainService extends UnicastRemoteObject implements IAdminMainService {
	
	private IAdminMainDao adminMainDao;
	
	public AdminMainService() throws RemoteException {
		adminMainDao = AdminMainDao.getInstance();
	}
	
}
