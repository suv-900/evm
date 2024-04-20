package com.project.host.services;

public class HostService {
    @Autowired
    private HostDao hostDao;
    
    @Autowired
    private PasswordHasher hasher;

    @Autowired
    private HostRepository repository;

    private final static Logger log = LoggerFactory.getLogger(HostService.class);

    public void addHost(HostEntity host)throws Exception{
        String hashedPassword = hasher.hashPassword(host.getPassword());

        host.setPassword(hashedPassword);

        hostDao.addHost(host);
    }

    public boolean loginHost(HostLogin host)throws Exception{
        String dbPassword = hostDao.getPasswordByUsername(host.getUsername());

        boolean matches = hasher.comparePassword(dbPassword,user.getPassword());

        return matches;
    }

    public HostEntity getHost(long hostID)throws Exception{
        return hostDao.findById(hostID);
    }

    public void updateHost(HostEntity host)throws Exception{
        hostDao.updateHost(host);
    }

    public deleteHost(HostEntity host)throws Exception{
        hostDao.deleteHost(host);
    }

    public void deleteByUsername(String username)throws Exception{
        hostRepository.deleteHostByUsername(username);
    }

    public boolean existsByUsername(String username)throws Exception{
        return hostRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email)throws Exception{
        return hostRepository.existsByEmail(email);
    }

    public boolean exists(HostEntity host)throws Exception{
        return hostDao.exists(host);
    }

    public List<HostEntity> findAllById(Iterable<Integer> idList)throws Exception{
        return hostDao.findAllById(idList);
    }
}

