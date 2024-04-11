# SFE4030-Project - WaitSmart

## Chapter 1 - Introduction to the Project
The invention of computers and advancement in technology has brought about automation of various tasks in different industries such as use of robots in cafeterias for order processing. The WaitSmart queue management documentation offers a comprehensive guide which provides insights into the features and implementation of WaitSmart. It is a system designed to streamline the process of remotely ordering and picking up food from the cafeteria. It provides a productive way to arrange and manage customer waiting areas, lessening customer annoyance and raising overall service quality.

WaitSmart queue management system's main objective is to improve the customer experience by reducing wait times, maintaining fair and orderly service, and giving customers access to real-time information and updates.

### Various Projects that we thought about:
  - Blogging  
  - AR Navigation  
  - Real Estate
    
### Reason that led us to do this project:  
One significant factor that drove our motivation was the identification of a major problem impacting individuals within our immediate community. Our primary objective was to tackle the problem of lengthy queues faced by students at USIU when trying to access ready-made meals, even when they had only few minutes to spare.  This realization compelled us to address this issue and develop a solution to streamline the meal ordering process for students. Our system, WaitSmart, aims to solve this common challenge faced by users in traditional cafeteria queues, such as long waiting times and disorganized order processing. By leveraging technology, WaitSmart provides a convenient and efficient solution that enhances the cafeteria experience for both users and the cafeteria staff.

#### Implementing our queue management system has the following advantages:
1. Increase in Customer satisfaction: As wait times are cut down, information is made clear, and a more equitable and orderly procedure is made possible. As a result, customers, in our case students and professors, are less irritated and have a better opinion of the service provider.

2. Enhanced operational effectiveness: The system assists service providers, in our case Sironi Restaurant staff, in resource optimization, demand-based staffing, and process enhancement. Better service delivery and more productivity are the benefits of this.

3. Real-time visibility: Managers and staff can keep an eye on client flow, spot busy times, and make wise choices to handle any problems as soon as they arise. Our system offers analytics and real-time data for improved operational control.

## Chapter 2 - Software Project Planning 
In order to successfully build and deploy our system, our planning entailed defining project goals, defining project scope, identifying tasks and milestones, estimating resources and schedules, and producing a road map. 

### Here is a model for our project planning procedure:
1. Project Goals and Purpose: Our goal was to create a queue management system which could enhance service effectiveness and customer flow. The system's features includes personnel management, balance management, and online ordering functionality.

2. Identification of Stakeholders: We determined the stakeholders and included them in the planning process. Some examples of stakeholders that were included are the Sironi management and students.

3. Requirements Gathering: After talking to the stakeholders, we compiled the necessary functional specifications, such as those for receipt creation, ordering food, and balance management. We then decided on the non-functional needs such as user experience, scalability, and security.

4. Project schedule and completion dates: Once we were done with identifying the requirements, we decided on the milestones for the project, such as design, development, testing, deployment, and maintenance. We set deadlines for milestones and an estimate of the length of each phase.

5. Identifying tasks and creating a work breakdown structure: Project tasks including requirement analysis, UI design, backend development, testing, and documentation was broken down into smaller jobs. We defined task dependencies and assigned tasks to each team members. Based on that information, we then made a WBS using Microsoft Project.

6. Risk evaluation and mitigation: We Identified potential risks, such as those caused by technological restrictions, or changes in needs.

7. Collaboration and Communication: We established avenues for communication, some of which included making WhatsApp group, organizing frequent meetings, and getting Github repository ready.

8. Tests and Quality Assurance: We created a plan for quality assurance activities, such as developing test scenarios, test cases, and acceptance criteria.

9. Implementation and Deployment: We defined the deployment plan while taking the needs of the software and hardware infrastructure into account. We contemplated about user education and change management procedures. Create a rollout strategy for pilot testing or staggered installation.

10. Project Control and Monitoring: We did follow up on milestones, keeping track of the project's progress, and handle any deviations from the original plan.

11. Information transfer and documentation: We were recording all information pertaining to the project, such as specifications, design documents, user guides, and technical documentation. In order to maintain and support the system, we ensured that any change made is known to everyone, and the changes were pushed to Github.

In this project, we have used the following:
- Front end - Java
- Backend - Firebase FireStore and Firebase Authentication
- Primary IDE - Android Studio
- Other Softwares used in this project:
  - Github - For collaboration and version control, which allowed all of us to work simultaneously and easily merge our changes. 
  - Adobe PhotoShop - For making mockups 
  - Adobe Illustrator - For making icons to be used in the project
  - Adobe XD - For making prototype
  - Adobe After Effects - For making promo video
  
### Code Generator to be used 

### Design to be used

### Work Breakdown Structure


## Chapter 3 - Software Models
### Model Appropriate to use and why
In our queue management system, which allows for remote food ordering and pickup, we have combined the strengths of the prototype and V-shaped model methodologies to ensure an effective, comprehensive and user centric development process. 

Prototype Model:   
  The prototype model played a crucial role in the development of our queue management system for remote food ordering and pickup. With the prototype model, we created an initial working version of the system that showcased the core features and functionalities some of which included remote food ordering and food pickup at ready time. This prototype served as a tangible representation of the system's concept and allowed stakeholders, including the school cafeteria management, students, and staff, to interact with it and provide valuable feedback.

  By developing a prototype, we can quickly validate and refine the system's design, functionality, and user experience. Stakeholders could test the prototype, explore its main features, and provide feedback on its usability, effectiveness, and quality. This iterative approach enabled us to incorporate their input and make necessary adjustments to improve the system's performance and meet their specific requirements. This iterative improvement did result in scope creep but we tried taking necessary measures to clearly define the project constraints (project scope, cost, and budget). In addition, the key features of the mobile application come first then other requirements can be included into the product once it’s out in production as updates or versions.

  Throughout the prototype development, we prioritized key features such as remote food ordering and order status notifications. This helped us focus on the core functionalities that are essential for the successful operation of the queue management system. By involving stakeholders early on and gathering their feedback, we ensured that the final system meets their needs, aligns with their expectations, delivers a seamless user experience, and is approved by the stakeholders.

V-Shaped Model:  
  The V-shaped model provided a structured and systematic framework for the development process of our queue management system. This model emphasizes a sequential flow of activities, starting from requirements analysis and leading to system deployment. Each stage in the V-shaped model has a corresponding testing phase, ensuring that the system is thoroughly tested at different levels to identify and rectify any issues or defects and ensure quality processes are used in developing the mobile application and the right product is been developed.

  In the requirements analysis stage, we worked closely with stakeholders to gather their requirements and expectations for the queue management system. We documented these requirements and used them as a basis for system design through the key software features. The system design phase involves creating a detailed blueprint of the system's architecture, components, and interactions. This design serves as a guide for the development team and ensures that the system is designed to meet the defined requirements.

  Once the system is designed, the implementation phase begins, where the actual coding and development take place. Following the implementation, we proceed to the testing phase. The V-shaped model emphasizes a comprehensive testing approach, including unit testing, integration testing, and system testing. This ensures that each component and stage of the system is tested individually and then integrated to ensure proper functionality and compatibility.

  The V-shaped model also includes user acceptance testing, where stakeholders and end-users validate the system against their requirements and expectations. This allows us to gather their feedback and make any necessary adjustments or refinements before the system is deployed.

  By following the V-shaped model, we ensure a disciplined and well-documented development process of each development stage. The model promotes traceability, as each stage is connected to its corresponding testing phase, allowing us to track and verify that the system meets the defined requirements and if any errors occur it becomes easy to roll back to where the issue is and rectify it. It also helps us adhere to industry standards and best practices, by allowing for systematic reviews and validations at each development stage, ensuring that the system meets the desired quality and compliance standards hence ensuring the reliability, stability, and quality of the queue management system. 

Combining the prototype and V-shaped model methodologies allowed us to benefit from the strengths of both approaches. The prototype model enabled us to gather early feedback and refine the system based on user input, while the V-shaped model ensured a structured and comprehensive development process, encompassing thorough testing and adherence to industry standards. By utilizing both models, we aimed to deliver a queue management system that is user-friendly, reliable, and meets the specific needs of our stakeholders.

## Chapter 4 - Requirements Gathering and Requirements Analysis
It involved the identification, documentation, analysis, and prioritization of requirements.
Fact-finding approaches included those that use direct observation, interviews, and documentation analysis to assemble data and identify requirements.

### Here are some fact-finding methods that we used:
1. Interviews:
We interviewed all relevant parties, including management, and end users. To learn more about their present queue management procedures, problems, and planned improvements, we provided open-ended questions. We then recorded the information obtained, mentioning particular functionalities, system restrictions, and integration needs.

2. Observation:
To comprehend the process, client interactions, and pain spots, we first saw the current system in use. We made a note of any delays, bottlenecks, or potential improvement areas for the system. We payed close attention to how employees and consumers interact and how lines are handled.

``` Remove this
3. Questionnaires:
To obtain input and requirements from a bigger number of stakeholders, we created questionnaires. We asked precise questions to elicit details such as desired features, service level expectations, or preferred contact routes.
We then examined the survey responses to find recurring themes, patterns, and areas that could want improvement.

4. Document evaluation
We examined current queue management paperwork, such as procedure manuals, service level agreements, or client satisfaction surveys and determined any requirements, restrictions, or problems that have been identified that the new system needs to address.

5. Use Case Evaluation
We determined important actors and their interactions with the queue management system, including customers, receptionists, and managers. We made use cases that detail the precise behaviors, inputs, and outputs of each system actor then we examined the use cases to find any extra system needs or undesirable system behaviors that might have gone unnoticed.
```

### For the Software Requirements Gathering we used the following techniques:
1. Determine Stakeholders: The main players in the software development project were identified, including the clients, end users, managers, and subject matter experts.
Then we determined each person's responsibilities, expectations, and roles in relation to the software.

2. Verification and Validation: We verified the requirements with the stakeholders to make sure they are accurate, comprehensive, and meet their needs. We also made sure that the requirements are testable and measurable, compare them to the established acceptance criteria.

3. Requirements Traceability: We ensured that the requirements, design components, test cases, and project deliverables are all traceable. We also asserted that every requirement is met and validated at every stage of the software development process.


## Chapter 5 - Functional Modelling.
It is the identification and definition of the various functional components and relationships within the system.
For this project, we used Activity diagram and Use-case diagram to identify the major functions of the system and also the relationships between the various processes in the system.

Activity diagram.

![image](https://github.com/yagneshmodi/APT2080-Project/assets/109243046/78c6d7aa-19e4-46b3-93c1-579178696488)


Use case diagram.

![image](https://github.com/yagneshmodi/APT2080-Project/assets/109243046/2d92787e-cad1-4938-8b04-3fe673ae313b)


## Chapter 6 - Structural Modelling  
It shows the architecture, components, and interactions are represented in the system.
It emphasizes on the system's static features, demonstrating how various components are arranged and interact with one another. 
For this project we used the Class Diagram where we identified the main classes and their attributes, methods, and relationships.

Class Diagram

![image](https://github.com/yagneshmodi/APT2080-Project/assets/109243046/080aedf6-c79c-4e6f-8509-8b764bda5854)


## Chapter 7 - Behavioral Modelling
It emphasizes on capturing the dynamic features of the system, showing how it acts and reacts to diverse events and stimuli. 
It describes the system's operations, communications, and work processes.
For this project, we used the Data Flow Diagram and the State Diagram.

Data Flow Diagram (DFD).

![DFD](https://github.com/yagneshmodi/APT2080-Project/assets/109243046/e3674657-6773-459e-81d8-36f5c504066d)

State Diagram

![image](https://github.com/yagneshmodi/APT2080-Project/assets/109243046/2c3e7d14-0d99-4b1c-991c-61d6dbcead46)


## Chapter 8 - Software Design
Two significant methods for designing software that put an emphasis on the user experience and efficient object-oriented design are user-centered design (UCD) and the GRASP (General Responsibility Assignment Software Patterns) principles. Here is how we put these ideas into practice:

1. User-Centered Design (UCD)
During the design process, user needs and preferences are understood and taken into consideration. Think about the following actions:  
    a. User research: A user research was conducted to learn more about the Queue Management System's intended audience. We identified  their objectives, responsibilities, and problems with queue management. User personas or user profiles were created to illustrate various user types.

    b. Use case analysis: We identified the use cases that represent how users interact with the queue management system. We made sure that the user interfaces were well thought out, simple to use, and visually appealing as we analyzed and developed them. User interface design was then evaluated using usability heuristics.

    c. Iterative Design: We used an iterative design process and included users in the testing and feedback stages. We made mockups or prototypes to collect user feedback and validate design choices. We utilized user feedback to enhance the design and user experience.

    d. Accessibility: We considered accessibility norms and standards to make sure a wide range of people could use the queue management system.

2. Principles of GRASP (General Responsibility Assignment Software Patterns)
To achieve efficient object-oriented design and appropriate responsibility distribution inside the Queue Management System, GRASP concepts were used. The process of design was influenced by the following GRASP guidelines:
    a. Creator: The ticket generation module, which has the data and context required for ticket production, was given the task of creating ticket objects.

    b. Information Specialist: Since the queue management module had the most up-to-date information for these activities, it was given the duty of maintaining the queue and updating queue positions.

    c. Controller: The role of organizing and directing the flow of tasks, including managing user requests and staff work, was delegated to a specific controller class.

    d. High coherence: To provide high cohesiveness and maintainability, related duties and functionalities were grouped together within classes or modules.

    e.Protected Subsequences: Modules and components were created to be easily extended or modified in response to changes.


## Chapter 9 - Software Testing
It made sure the system worked as expected, complied with the requirements, and offered a dependable and effective method of handling queues in diverse contexts. This section describes the testing methodology, testing approach, and testing tactics we used to evaluate and verify the WaitSmart system's performance, security, and functionality.
 
Software testing for the WaitSmart queue management system had the following main goals as its starting point:
1. To make sure that all of the WaitSmart Queue Management System's features and functions functioned as they should.
2. To verify that the system operated correctly and effectively under various load circumstances.
3. To make sure the system was safe, guarding private information, and preventing illegal access.
4. To verify that both staff and customers had a smooth experience using the system and that it was user-friendly.

### Testing strategies
We used the following testing strategies to achieve thorough testing coverage:
1. Functional Testing: We confirmed that every system function adhered to functional requirements and performed as intended.

2. Testing for Smooth Integration: To achieve a seamless integration, we examined the interactions between various components.

3. Usability testing: We assessed the user interface's usability, logicalness, and overall usability.

4. User Acceptance Testing (UAT): To ensure the system complied with our users' needs, we enlisted their help.

5. Load and Stress Testing: We performed load and stress testing to make sure the WaitSmart system could manage real-world traffic. To assess the system's performance and scalability, severe loads were simulated and applied to the system. The system passed the tests and showed that it could manage a sizable number of concurrent users without sacrificing speed.

Test Execution and Results: Every test case and scenario were methodically carried out in their appropriate contexts during the testing phase. To find any differences between anticipated and actual findings, the test results were recorded and examined. We closely worked together to address and fix any problems that were found during testing.

User Input and Incorporation: The WaitSmart Queue Management System was greatly improved as a result of user input received during the User Acceptance Testing (UAT) phase. To improve the system's usability and effectiveness, end users' insightful comments and ideas were carefully evaluated.

## Chapter 10 - Software Implementation & Maintenance
- Changeover Method
- Implementation Strategy
- Maintenance Schedule

### Process of Implementation:  
The WaitSmart Queue Management System will be put into place in a methodical way, sticking to the important steps listed below:
1. Analysis of Requirements  
To make sure that the WaitSmart system handles all crucial operations and features, we did a thorough analysis of the business demands and user requirements.
2. Design and Development  
In accordance with the requirements and best practices in the industry, our talented team of developers worked carefully to design and construct the WaitSmart system.
3. Testing and quality control  
User acceptance testing, unit testing, and integration testing were all conducted throughout the WaitSmart system's development cycle. This method of testing made sure the system was stable, strong, and matched user expectations.
4. Deployment  
To ensure a seamless and effective distribution, we plan on implementing the system using phased method. Reason for this is that, we will be addressing immediate needs, we will be getting early user feedback which can then be used to mitigate the risk. 

### Maintenance and Support.
To guarantee the WaitSmart Queue Management System's optimum performance and dependability, ongoing maintenance and support will be provided. 
Our maintenance strategy entails:
1. Regular Updates and Enhancements  
In order to fix any problems, add new features, and enhance system performance, we will regularly release software updates and improvements.
2. Bug fixes and problem resolution
In the event that there are any bugs or technical problems, we will take immediate action to ensure the stability and functionality of the system.
3. Data backup and disaster recovery.
To prevent data loss, regular data backups will be done, and disaster recovery mechanisms will be put in place to restore the system in the event of any unforeseen circumstances.
4. Evolving Needs.
The WaitSmart system will be upgraded and maintained to respond to changing needs as the business and user requirements change.
5. Scheduled Downtime.
To reduce disruptions, maintenance windows will be set aside during non-peak times. Any anticipated downtime will be announced in advance to users.


## Colors
| | | | | |  
|---|---|---|---|---|
| ![#fc6011](https://placehold.co/75x75/fc6011/fc6011.png) | ![#4a4b4d](https://placehold.co/75x75/4a4b4d/4a4b4d.png) | ![#7c7d7e](https://placehold.co/75x75/7c7d7e/7c7d7e.png) | ![#b6b7b7](https://placehold.co/75x75/b6b7b7/b6b7b7.png) | ![#ffffff](https://placehold.co/75x75/ffffff/ffffff.png) |
|<p align="center">`#fc6011`</p>  | <p align="center">`#4a4b4d`</p> | <p align="center">`#7c7d7e`</p> | <p align="center">`#b6b7b7`</p> | <p align="center">`#ffffff`</p>
