BEGIN;
    alter table public.dogs add column name_clone text default null;
    update public.dogs set name_clone = name;

    ALTER TABLE public.walks DROP CONSTRAINT walks_dog_name_fkey;
    alter table walks
        add constraint walks_dog_name_fkey    foreign key (dog_name)    REFERENCES dogs (name) on update cascade on delete cascade;

    update public.dogs set name = gen_random_uuid();

    ALTER TABLE dogs RENAME COLUMN name TO id;
    ALTER TABLE dogs RENAME COLUMN name_clone TO name;

    ALTER TABLE walks RENAME COLUMN dog_name TO dog_id;
COMMIT;