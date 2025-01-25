package com.companystrator.config;

import com.companystrator.db.model.User;
import com.companystrator.exceptions.exception.JWTTokenNotProvidedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.IOException;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

	@Value("${application.security.jwt.private-key-path}")
	private String PRIVATE_KEY_PATH;

	@Value("${application.security.jwt.public-key-path}")
	private String PUBLIC_KEY_PATH;

	private static final long TOKEN_EXPIRATION_TIME = 15 * 60 * 1000;
	private static final long REFRESH_TOKEN_EXPIRATION_TIME = 30L * 24L * 60L * 60L * 1000L;
	private PrivateKey privateKey;
	private PublicKey publicKey;

	@PostConstruct
	public void init() {
		try {
			this.privateKey = loadPrivateKey(PRIVATE_KEY_PATH);
			this.publicKey = loadPublicKey(PUBLIC_KEY_PATH);
		} catch (IOException e) {
			throw new RuntimeException("Error loading keys: " + e.getMessage(), e);
		}
	}

	public String extractUsernameFromRequest(HttpServletRequest request) {
		try {
			String token = request.getHeader("Authorization").substring(7);
			return this.extractUsername(token);
		} catch (Exception e) {
			throw new JWTTokenNotProvidedException("Header Authorization not provided or not valid");
		}
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	public boolean isTokenNonExpired(String token) {
		return !extractExpiration(token).before(new Date());
	}

	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public String extractRole(String token) {
		return extractClaim(token, claims -> claims.get("role", String.class));
	}

	private Claims extractAllClaims(String token) {
		return Jwts
			.parserBuilder()
			.setSigningKey(publicKey)
			.build()
			.parseClaimsJws(token)
			.getBody();
	}

	public String generateToken(User userDetails, boolean isRefreshToken) {
		return generateToken(new HashMap<>(), userDetails, isRefreshToken);
	}

	public String generateToken(Map<String, Object> extraClaims, User userDetails, boolean isRefreshToken) {
		return buildToken(extraClaims, userDetails, isRefreshToken);
	}

	public String buildToken(Map<String, Object> extraClaims, User userDetails, boolean isRefreshToken) {
		extraClaims.put("role", userDetails.getRole());
		return Jwts
			.builder()
			.setClaims(extraClaims)
			.setSubject(userDetails.getUsername())
			.setIssuedAt(new Date(System.currentTimeMillis()))
			.setExpiration(new Date(System.currentTimeMillis() + (isRefreshToken ? REFRESH_TOKEN_EXPIRATION_TIME : TOKEN_EXPIRATION_TIME)))
			.signWith(privateKey, SignatureAlgorithm.ES512)
			.compact();
	}

	private PrivateKey loadPrivateKey(String path) throws IOException {
		try {
			InputStream inputStream = new ClassPathResource(path).getInputStream();
			String keyContent = new String(inputStream.readAllBytes())
				.replaceAll("-----BEGIN PRIVATE KEY-----", "")
				.replaceAll("-----END PRIVATE KEY-----", "")
				.replaceAll("\\s+", "");
			byte[] keyBytes = Decoders.BASE64.decode(keyContent);
			PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance("EC");
			return keyFactory.generatePrivate(spec);
		} catch (Exception e) {
			throw new RuntimeException("Failed to load private key", e);
		}
	}

	private PublicKey loadPublicKey(String path) throws IOException {
		try {
			InputStream inputStream = new ClassPathResource(path).getInputStream();
			String keyContent = new String(inputStream.readAllBytes())
				.replaceAll("-----BEGIN PUBLIC KEY-----", "")
				.replaceAll("-----END PUBLIC KEY-----", "")
				.replaceAll("\\s+", "");
			byte[] keyBytes = Decoders.BASE64.decode(keyContent);
			X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance("EC");
			return keyFactory.generatePublic(spec);
		} catch (Exception e) {
			throw new RuntimeException("Failed to load public key", e);
		}
	}

}
