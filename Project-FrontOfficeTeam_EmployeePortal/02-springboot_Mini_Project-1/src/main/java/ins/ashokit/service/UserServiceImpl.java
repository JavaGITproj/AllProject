package ins.ashokit.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ins.ashokit.binding.LoginForm;
import ins.ashokit.binding.SignUpForm;
import ins.ashokit.binding.UnlockForm;
import ins.ashokit.constant.Appconstant;
import ins.ashokit.entity.UserdtlsEntity;
import ins.ashokit.repo.UserDtlsRepo;
import ins.ashokit.util.EmailUtil;
import ins.ashokit.util.PwdUtils;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private HttpSession session;

	@Autowired
	private UserDtlsRepo userDtlsRepo;

	@Autowired
	private EmailUtil email;

	@Override
	public boolean signup(SignUpForm form) {

		UserdtlsEntity findByEmail = userDtlsRepo.findByEmail(form.getEmail());

		if (findByEmail != null) {
			return false;
		}

		// copying data from binding obj to entity object
		UserdtlsEntity entity = new UserdtlsEntity();
		BeanUtils.copyProperties(form, entity);

		//  generate random password
		String temppwd = PwdUtils.generateRandomPwd();
		entity.setPwd(temppwd);

		//  send account status as Locked

		entity.setAcc_status(Appconstant.STR_LOCKED);

		//insert record

		userDtlsRepo.save(entity);

		//send the mail to unlock the account
		String to = form.getEmail();
		String subject = Appconstant.STR_EMAIL_SUBJECT;
		StringBuilder body = new StringBuilder("");
		body.append("<h2>use below temporary password  to unclok your account</h2>");
		body.append("temporary password: " + temppwd);
		body.append("<br/>");
		body.append("<a href=\"http://localhost:9090/unlock?email=" + to + "\">click here to unlock");
		email.sendEmail(to, subject, body.toString());
		return true;
	}

	@Override
	public String login(LoginForm form) {
		
		UserdtlsEntity status = userDtlsRepo.findByEmailAndPwd(form.getEmail(),form.getPwd());
		if(status==null) {
			return Appconstant.STR_INVALID_CREDENTIAL;
		}
		if(status.getAcc_status().equals(Appconstant.STR_LOCKED)) {
			return Appconstant.STR_ACC_STATUS_MSG;
		}
		//create session and store user data
		 session.setAttribute("userId", status.getUserId());
		return Appconstant.STR_SUCCESS_MSG;
	}

	@Override
	public boolean forgotpwd(String emailform) {
		//check the record with given email
		UserdtlsEntity findByEmail = userDtlsRepo.findByEmail(emailform);
		//if not record is not available then send  errr msg
		if(findByEmail==null) {			
			return false;
		}		
		//if record available send email for recovery pwd
		String subject=Appconstant.STR_RECOVER_PWWZD;
		String body="Your pwd::"+findByEmail.getPwd();		
		email.sendEmail(emailform, subject, body);
		
		return true;
	}

	@Override
	public boolean unlockAccount(UnlockForm form) {
		UserdtlsEntity entity = userDtlsRepo.findByEmail(form.getEmail());

		if (entity.getPwd().equals(form.getTemppdw())) {
			entity.setPwd(form.getNewpdw());
			entity.setAcc_status(Appconstant.STR_UNLOCKED);
			userDtlsRepo.save(entity);
			return true;
		} else {

			return false;
		}

	}

}
