package hu.evocelot.sample.model;

import java.math.BigInteger;
import java.time.OffsetDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

/**
 * Base class for all entities that require audit fields.
 * <p>
 * This abstract class provides common fields for entities that need to track
 * creation and modification dates, user information, and versioning. It is
 * designed to be inherited by other entity classes that require audit
 * functionality, ensuring consistency and reusability across the application.
 * </p>
 * <p>
 * The audit fields include:
 * <ul>
 * <li>insDate: The timestamp of when the entity was created.</li>
 * <li>insUser: The user who created the entity.</li>
 * <li>modDate: The timestamp of the last modification made to the entity.</li>
 * <li>modUser: The user who last modified the entity.</li>
 * <li>version: A version number used for optimistic locking in concurrency
 * control.</li>
 * </ul>
 * </p>
 * <p>
 * All the fields are automatically populated upon entity creation and
 * modification, with placeholders for the user fields that should be
 * dynamically set (e.g., using the current authenticated user).
 * </p>
 * 
 * @author mark.danisovszky
 */
@MappedSuperclass
public class AbstractIdentifiedAuditEntity {

    /**
     * Default constructor initializing audit fields.
     * <p>
     * The constructor sets the creation and modification dates to the current
     * timestamp. The user fields are set to "unknown" as placeholders, which should
     * be replaced by the current authenticated user at runtime. The version field
     * is initialized to 0.
     * </p>
     */
    public AbstractIdentifiedAuditEntity() {
        insDate = OffsetDateTime.now();
        insUser = "unknown"; // TODO: Get current user.
        modDate = OffsetDateTime.now();
        modUser = "unknown"; // TODO: Get current user.
        version = BigInteger.valueOf(0);
    }

    /**
     * The unique identifier for the entity.
     * <p>
     * This field is automatically generated using UUID as the strategy for unique
     * identification.
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ID", length = 36, nullable = false, unique = true)
    private String id;

    /**
     * The timestamp of when the entity was created.
     * <p>
     * This field is automatically set to the current time when the entity is
     * created.
     * </p>
     */
    @Column(name = "INS_DATE", nullable = false)
    private OffsetDateTime insDate;

    /**
     * The user who created the entity.
     * <p>
     * This field is a placeholder for the user who initiated the creation of the
     * entity. It should be dynamically set based on the currently authenticated
     * user.
     * </p>
     */
    @Column(name = "INS_USER", nullable = true)
    private String insUser;

    /**
     * The timestamp of the last modification made to the entity.
     * <p>
     * This field is updated whenever the entity is modified.
     * </p>
     */
    @Column(name = "MOD_DATE", nullable = false)
    private OffsetDateTime modDate;

    /**
     * The user who last modified the entity.
     * <p>
     * This field should be set dynamically, ideally with the user who performed the
     * last modification.
     * </p>
     */
    @Column(name = "MOD_USER", nullable = true)
    private String modUser;

    /**
     * The version number of the entity.
     * <p>
     * This field is used for optimistic locking to handle concurrent updates to the
     * entity. The version is incremented each time the entity is updated,
     * preventing conflicting changes.
     * </p>
     */
    @Column(name = "VERSION", nullable = false)
    private BigInteger version;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public OffsetDateTime getInsDate() {
        return insDate;
    }

    public void setInsDate(OffsetDateTime insDate) {
        this.insDate = insDate;
    }

    public String getInsUser() {
        return insUser;
    }

    public void setInsUser(String insUser) {
        this.insUser = insUser;
    }

    public OffsetDateTime getModDate() {
        return modDate;
    }

    public void setModDate(OffsetDateTime modDate) {
        this.modDate = modDate;
    }

    public String getModUser() {
        return modUser;
    }

    public void setModUser(String modUser) {
        this.modUser = modUser;
    }

    public BigInteger getVersion() {
        return version;
    }

    public void setVersion(BigInteger version) {
        this.version = version;
    }
}
