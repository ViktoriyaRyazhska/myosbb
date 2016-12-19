package com.softserve.osbb.service.impl;

import java.util.Objects;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.softserve.osbb.model.Osbb;
import com.softserve.osbb.model.Settings;
import com.softserve.osbb.model.User;
import com.softserve.osbb.service.OsbbService;
import com.softserve.osbb.service.RegistrationService;
import com.softserve.osbb.service.RoleService;
import com.softserve.osbb.service.SettingsService;
import com.softserve.osbb.service.UserService;

/**
 * Created by nazar.dovhyy on 29.10.2016.
 */
@Service
public class RegistrationServiceImpl implements RegistrationService {

    private static final Logger logger = LoggerFactory.getLogger(RegistrationServiceImpl.class);
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private OsbbService osbbService;
    
    @Autowired
    private SettingsService settingsService;
    
    @Autowired
    private RoleService roleService;
    
    private  String[] alphabet = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
	private Random  random = new Random();
	private String password;
	private static final int MIN_LENGTH = 4;
	private static final int MAX_LENGTH = 13;
	private static final int MAX_THRESHOLD = 3;
	private static final int MIN_THRESHOLD = 1;
	private static final int INDEX_OF_LOWERCASE_LETTER = 1;
	private static final int INDEX_OF_UPPERCASE_LETTER = 2;
	private static final int DIVIDER = 3;
	private static final int RANDOM_AREA = 10;
	
	private int generatePasswordLenght() {
		return random.nextInt(MAX_LENGTH) + MIN_LENGTH;
	}
	
	private int generateRandomThreshold() {
		return random.nextInt(MAX_THRESHOLD) + MIN_THRESHOLD;
	}
	
	private void checkUserPassword(User user) {
		if(Objects.isNull(user.getPassword())) {
        	user.setPassword(generatePassword());
        }
	}
	
	public String generatePassword() {
		StringBuilder password = new StringBuilder();
        int lenght = generatePasswordLenght();
        
        for(int ind = 0; ind < lenght; ind++) {
        	int randThreshold = generateRandomThreshold();
  
            if(randThreshold % DIVIDER == INDEX_OF_LOWERCASE_LETTER) {
                password.append(alphabet[random.nextInt(alphabet.length)]);
            }
            else if(randThreshold % DIVIDER == INDEX_OF_UPPERCASE_LETTER) {
                password.append(alphabet[random.nextInt(alphabet.length)].toUpperCase());
            }
            else{
                password.append(random.nextInt(RANDOM_AREA));
            }
        }
       return (this.password = password.toString());
    }

    @Transactional(readOnly = false,
            isolation = Isolation.SERIALIZABLE,
            propagation = Propagation.REQUIRED)
    @Override
    public User registrate(User user) {
        logger.info("registrating new user " + user);
        checkUserPassword(user);
        User registeredUser = userService.save(user);
        settingsService.save(new Settings(registeredUser));
        return registeredUser;
    }

    @Transactional(readOnly = false,
            isolation = Isolation.SERIALIZABLE,
            propagation = Propagation.REQUIRED)
    @Override
    public Osbb registrate(Osbb newOsbb) {
        logger.info("registering new osbb " + newOsbb);
        if (!osbbService.findByNameContaining(newOsbb.getName()).isEmpty()) {
            throw new IllegalArgumentException("osbb with such name " + newOsbb.getName() + " already exists");
        }
        User creator = newOsbb.getCreator();
        creator = registrate(creator);
        newOsbb = osbbService.addOsbb(newOsbb);
        creator.setOsbb(newOsbb);
        creator.setRole(roleService.getRole("ROLE_MANAGER"));
        userService.update(creator);
        return newOsbb;
    }

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public void setPassword(String password) {
		this.password = password;
	}
    
}
