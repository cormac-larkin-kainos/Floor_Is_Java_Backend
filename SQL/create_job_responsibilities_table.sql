CREATE TABLE responsibility (
    responsibility_id SMALLINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    responsibility_desc TEXT
);

INSERT INTO responsibility (responsibility_desc)
            VALUES
            ('Developing high quality solutions which delight our customers and impact the lives of users worldwide'),
            ('Making sound, reasoned decisions')
            ('Learning about new technologies and approaches'),
            ('Mentoring those around you'),
            ('Leading the architecture on product-based projects'),
            ('Analysing the gap between product offering and customer needs'),
            ('Designing technical solutions with the right blend of configuration, customisation, integration and bespoke development needed to deliver a working end to end system'),
            ('Leading multi-skilled agile teams to combine configuration and custom development to deliver high quality solutions'),
            ('Working with customer architects to agree functional and non-functional designs, advising customers and managers on the estimated effort, technical implications and complexity'),
            ('Managing, coaching and developing a small number of staff, with a focus on managing employee performance and assisting in their career development'),
            ('Providing direction and leadership for your team as you solve challenging problems together'),
            ('Working with your peers to develop policy and standards, share knowledge and mentor those around you'),
            ('Advising about new technologies, approaches and innovations in your area of specialism'),
            ('Leading teams'),
            ('Interacting with customers'),
            ('Sharing knowledge'),
            ('Developing and executing functional automated and manual tests to help the team deliver working application software that meets user needs'),
            ("Collaborating with the Innovation Lead and Director of Innovation to shape the company's strategy for innovation and connect strategy with tactical implementation"),
            ('Assuming a leadership position in driving the approaches and tooling required for dynamic R&D projects'),
            ('Actively engaging with the wider technology community, including conferences, meetups and events to continue to demonstrate thought leadership in public forums'),
            ('Identifying and implementing process improvements to improve the effectiveness of the innovation team'),
            ('Taking responsibility for the Innovation Team’s internal and external communications strategy to share knowledge and demonstrate leadership both internally and in the public domain'),
            ('Coordinating with other parts of the business, including presenting to internal and external customers'),
            ('People Development'),
            ('Strategy'),
            ('Business Development'),
            ('Communication'),
            ('Partner Development'),
            ('Innovation'),
            ('Infosec: Accountable for delivery-focused Infosec across the organisation'),
            ('Delivery and Assurance')
            ;


    CREATE TABLE job_responsibility (
          job_id SMALLINT UNSIGNED,
          responsibility_id SMALLINT UNSIGNED,
          PRIMARY KEY (job_id, responsibility_id),
          FOREIGN KEY (job_id) REFERENCES job(job_id),
          FOREIGN KEY (responsibility_id) REFERENCES responsibility(responsibility_id)
      );

   INSERT INTO job_responsibility VALUES
   (1, 1),
   (1, 2),
   (1, 3),
   (1, 4),
   (2, 5),
   (2, 6),
   (2, 7),
   (2, 8),
   (2, 9),
   (2, 10),
   (2, 11),
   (2, 12),
   (2, 13),
   (3, 14),
   (3, 1),
   (3, 2),
   (3, 3),
   (3, 10),
   (3, 11),
   (3, 15),
   (3, 16),
   (3, 4),
   (4, 17),
   (4, 3),
   (5, 18),
   (5, 19),
   (5, 20),
   (5, 21),
   (5, 22),
   (5, 23),
   (5, 10),
   (6, 24),
   (6, 25),
   (6, 26),
   (6, 27),
   (6, 28),
   (6, 29),
   (6, 30),
   (6, 31)
   (7, 1),
   (7, 14),
   (7, 9),
   (7, 10),
   (7, 2),
   (7, 3),
   (8, 1),
   (8, 3)
   ;





