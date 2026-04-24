# 🤝 Contributing to MilkMate

Thank you for your interest in contributing to the MilkMate Dairy Management System! This document provides guidelines and processes for contributing to the project.

## 📋 Table of Contents
- [Code of Conduct](#code-of-conduct)
- [How to Contribute](#how-to-contribute)
- [Development Workflow](#development-workflow)
- [Coding Standards](#coding-standards)
- [Testing Guidelines](#testing-guidelines)
- [Documentation](#documentation)
- [Pull Request Process](#pull-request-process)
- [Reporting Issues](#reporting-issues)

## 🤝 Code of Conduct

### Our Standards
- **Respect**: Treat all contributors with respect and kindness
- **Inclusivity**: Welcome contributions from everyone
- **Professionalism**: Maintain professional communication
- **Constructive Feedback**: Provide helpful, actionable feedback
- **Collaboration**: Work together towards common goals

### Unacceptable Behavior
- Harassment or discrimination
- Personal attacks or insults
- Inappropriate language or imagery
- Disruptive conduct
- Publishing others' private information

## 🛠️ How to Contribute

### Ways to Contribute
1. **Code Contributions**
   - Bug fixes
   - New features
   - Performance improvements
   - Code refactoring

2. **Documentation**
   - User guides
   - Technical documentation
   - API documentation
   - Tutorials

3. **Testing**
   - Writing test cases
   - Bug reporting
   - Performance testing
   - User acceptance testing

4. **Design**
   - UI/UX improvements
   - Template enhancements
   - Visual design elements

5. **Community**
   - Answering questions
   - Reviewing pull requests
   - Translating documentation
   - Spreading awareness

## 🔄 Development Workflow

### Getting Started
1. **Fork the Repository**
   ```bash
   # Click "Fork" button on GitHub
   # Clone your fork
   git clone https://github.com/your-username/Milkmate.git
   cd Milkmate
   ```

2. **Set Up Development Environment**
   ```bash
   # Follow INSTALLATION_GUIDE.md
   mvn clean install
   ```

3. **Create Feature Branch**
   ```bash
   git checkout -b feature/your-feature-name
   ```

### Development Process
1. **Issue Creation**
   - Check existing issues first
   - Create new issue with clear description
   - Include steps to reproduce (for bugs)
   - Add labels (bug, enhancement, documentation)

2. **Branch Naming Convention**
   ```
   feature/new-feature-name
   bugfix/issue-description
   hotfix/critical-fix
   docs/documentation-update
   ```

3. **Development Guidelines**
   - Work on one issue per branch
   - Keep commits small and focused
   - Write meaningful commit messages
   - Follow coding standards
   - Add tests for new functionality

4. **Before Committing**
   ```bash
   # Run tests
   mvn test
   
   # Check code quality
   mvn checkstyle:check
   
   # Ensure build passes
   mvn clean install
   ```

## 💻 Coding Standards

### Java Code Standards

#### Naming Conventions
```java
// Classes - PascalCase
public class ProductService { }

// Methods - camelCase
public void calculateTotalAmount() { }

// Variables - camelCase
private String productName;
private int productCount;

// Constants - UPPER_SNAKE_CASE
public static final String DEFAULT_ADMIN_EMAIL = "admin@gmail.com";
```

#### Code Structure
```java
public class ExampleController {
    
    // Dependencies first
    @Autowired
    private ProductService productService;
    
    @Autowired
    private UserRepository userRepository;
    
    // Constants
    private static final String SUCCESS_MESSAGE = "Operation completed successfully";
    
    // Public methods
    @GetMapping("/example")
    public String exampleMethod(@RequestParam String param, Model model) {
        // Method implementation
        return "view-name";
    }
    
    // Private helper methods
    private void validateInput(String input) {
        // Validation logic
    }
}
```

#### JPA Entity Standards
```java
@Entity
@Table(name = "products")
public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotEmpty(message = "Product name is required")
    private String name;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    // ... other getters/setters
    
    // equals, hashCode, toString (when needed)
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Product product = (Product) obj;
        return Objects.equals(id, product.id);
    }
}
```

#### Controller Standards
```java
@Controller
@RequestMapping("/product")
public class ProductController {
    
    @Autowired
    private ProductService productService;
    
    // List view
    @GetMapping
    public String listProducts(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "product/list";
    }
    
    // Add form
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("product", new Product());
        return "product/form";
    }
    
    // Create
    @PostMapping("/add")
    public String createProduct(@Valid Product product, 
                               BindingResult result, 
                               RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "product/form";
        }
        
        try {
            productService.saveProduct(product);
            redirectAttributes.addFlashAttribute("success", "Product created successfully");
            return "redirect:/product";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to create product");
            return "redirect:/product/add";
        }
    }
    
    // Edit
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id);
        if (product == null) {
            return "redirect:/product";
        }
        model.addAttribute("product", product);
        return "product/form";
    }
    
    // Delete
    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            productService.deleteProduct(id);
            redirectAttributes.addFlashAttribute("success", "Product deleted successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to delete product");
        }
        return "redirect:/product";
    }
}
```

### HTML/Thymeleaf Standards
```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title th:text="${title}">Page Title</title>
    <link href="/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <!-- Header -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
        <!-- Navigation content -->
    </nav>
    
    <!-- Main Content -->
    <div class="container mt-4">
        <div class="row">
            <div class="col-md-12">
                <!-- Page content -->
            </div>
        </div>
    </div>
    
    <!-- Footer -->
    <footer class="footer mt-auto py-3 bg-light">
        <div class="container">
            <span class="text-muted">© 2024 MilkMate</span>
        </div>
    </footer>
    
    <!-- Scripts -->
    <script src="/js/bootstrap.bundle.min.js"></script>
</body>
</html>
```

### CSS/JavaScript Standards
```css
/* Use Bootstrap classes primarily */
/* Custom CSS only when necessary */

.custom-button {
    margin: 10px 0;
    padding: 8px 16px;
}

.table-responsive {
    overflow-x: auto;
}
```

## 🧪 Testing Guidelines

### Test Structure
```
src/test/java/com/DM/dairyManagement/
├── service/
│   ├── ProductServiceTest.java
│   ├── UserServiceTest.java
│   └── BillServiceTest.java
├── controller/
│   ├── ProductControllerTest.java
│   └── UserControllerTest.java
└── repository/
    └── ProductRepositoryTest.java
```

### Unit Test Example
```java
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class ProductServiceTest {
    
    @Autowired
    private ProductService productService;
    
    @Autowired
    private ProductRepository productRepository;
    
    @MockBean
    private StockService stockService;
    
    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
    }
    
    @Test
    void testSaveProduct() {
        // Given
        Product product = new Product();
        product.setName("Full Cream Milk");
        product.setDescription("Fresh full cream milk");
        
        // When
        Product savedProduct = productService.saveProduct(product);
        
        // Then
        assertThat(savedProduct.getId()).isNotNull();
        assertThat(savedProduct.getName()).isEqualTo("Full Cream Milk");
        assertThat(productRepository.count()).isEqualTo(1);
    }
    
    @Test
    void testGetProductById_NotFound() {
        // When & Then
        assertThrows(EntityNotFoundException.class, 
            () -> productService.getProductById(999L));
    }
    
    @Test
    @Transactional
    void testDeleteProduct() {
        // Given
        Product product = new Product();
        product.setName("Test Product");
        Product savedProduct = productService.saveProduct(product);
        
        // When
        productService.deleteProduct(savedProduct.getId());
        
        // Then
        assertThat(productRepository.findById(savedProduct.getId())).isEmpty();
    }
}
```

### Integration Test Example
```java
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@TestMethodOrder(OrderAnnotation.class)
class ProductControllerIntegrationTest {
    
    @Autowired
    private TestRestTemplate restTemplate;
    
    @Autowired
    private ProductRepository productRepository;
    
    @Test
    @Order(1)
    void testCreateProduct() {
        // Given
        Product product = new Product();
        product.setName("Integration Test Product");
        product.setDescription("Test Description");
        
        // When
        ResponseEntity<Product> response = restTemplate.postForEntity(
            "/api/products", product, Product.class);
        
        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("Integration Test Product");
    }
    
    @Test
    @Order(2)
    void testGetAllProducts() {
        // When
        ResponseEntity<List<Product>> response = restTemplate.exchange(
            "/api/products", HttpMethod.GET, null, 
            new ParameterizedTypeReference<List<Product>>() {});
        
        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotEmpty();
    }
}
```

## 📚 Documentation Standards

### Javadoc Guidelines
```java
/**
 * Product Service for managing dairy products.
 * Provides CRUD operations and business logic for products.
 * 
 * @author Your Name
 * @since 1.0.0
 */
@Service
public class ProductService {
    
    /**
     * Retrieves all products from the database.
     * 
     * @return List of all products ordered by name
     * @throws DataAccessException if database operation fails
     */
    public List<Product> getAllProducts() {
        try {
            return productRepository.findAllByOrderByName();
        } catch (DataAccessException e) {
            throw new ServiceException("Failed to retrieve products", e);
        }
    }
    
    /**
     * Saves a product to the database.
     * 
     * @param product the product to save (must not be null)
     * @return the saved product with generated ID
     * @throws IllegalArgumentException if product is null
     * @throws ValidationException if product data is invalid
     */
    public Product saveProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        
        validateProduct(product);
        return productRepository.save(product);
    }
}
```

### README Updates
When adding new features:
- Update feature list
- Add new screenshots
- Document new configuration options
- Update installation instructions if needed

## 📥 Pull Request Process

### PR Checklist
Before submitting a pull request, ensure:

- [ ] Code follows project coding standards
- [ ] All tests pass (`mvn test`)
- [ ] New functionality includes tests
- [ ] Documentation is updated
- [ ] Commit messages are clear and descriptive
- [ ] Branch is up to date with main
- [ ] No merge conflicts

### PR Template
```markdown
## Description
Brief description of changes made.

## Related Issue
Fixes #issue-number

## Type of Change
- [ ] Bug fix
- [ ] New feature
- [ ] Breaking change
- [ ] Documentation update

## How Has This Been Tested?
- [ ] Unit tests
- [ ] Integration tests
- [ ] Manual testing

## Checklist
- [ ] My code follows the style guidelines
- [ ] I have performed a self-review
- [ ] I have commented my code
- [ ] I have made corresponding changes to documentation
- [ ] My changes generate no new warnings
```

### PR Review Process
1. **Automated Checks**
   - Build status
   - Test coverage
   - Code quality metrics

2. **Manual Review**
   - Code quality
   - Functionality
   - Security considerations
   - Performance impact

3. **Approval**
   - At least one approval required
   - All comments addressed
   - CI/CD checks passed

## 🐛 Reporting Issues

### Issue Template
```markdown
## Description
Clear and concise description of the issue.

## Steps to Reproduce
1. Go to '...'
2. Click on '....'
3. Scroll down to '....'
4. See error

## Expected Behavior
What you expected to happen.

## Actual Behavior
What actually happened.

## Screenshots
If applicable, add screenshots to help explain.

## Environment
- OS: [e.g. Windows 10, macOS 12]
- Browser: [e.g. Chrome, Safari]
- Version: [e.g. 22]
- Java Version: [e.g. 17]
- Database: [e.g. MySQL 8.0]

## Additional Context
Add any other context about the problem.
```

### Issue Labels
- `bug` - Something isn't working
- `enhancement` - New feature or request
- `documentation` - Documentation improvement
- `help wanted` - Extra attention needed
- `good first issue` - Good for newcomers
- `question` - Further information requested

## 🎯 Getting Started for New Contributors

### First Contribution Steps
1. **Find an Issue**
   - Look for `good first issue` or `help wanted` labels
   - Comment on the issue to claim it

2. **Set Up Environment**
   - Follow INSTALLATION_GUIDE.md
   - Run the application locally

3. **Make Changes**
   - Create feature branch
   - Implement the fix/feature
   - Write tests
   - Update documentation

4. **Submit PR**
   - Push to your fork
   - Create pull request
   - Address feedback

### Need Help?
- Join our discussion forum
- Contact maintainers
- Check existing documentation
- Ask in community chat

## 🏆 Recognition

### Contributor Recognition
- **GitHub Contributors Page**: All contributors listed
- **Release Notes**: Contributors mentioned in releases
- **Documentation**: Contributor names in docs
- **Community Shoutouts**: Recognition in community channels

### Contribution Types We Value
- Code contributions
- Bug reports
- Documentation improvements
- Feature requests
- Community support
- Testing and feedback

---

## 📞 Contact

For questions about contributing:
- **Email**: maintainers@milkmate.com
- **GitHub Discussions**: Use the discussions tab
- **Issue Tracker**: For bug reports and feature requests

Thank you for contributing to MilkMate! 🐄✨

<p align="center">Happy coding!</p>